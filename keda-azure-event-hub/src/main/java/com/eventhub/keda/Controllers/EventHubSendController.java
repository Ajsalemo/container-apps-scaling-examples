package com.eventhub.keda.Controllers;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.messaging.eventhubs.EventProcessorClientBuilder;
import com.azure.messaging.eventhubs.checkpointstore.blob.BlobCheckpointStore;
import com.azure.messaging.eventhubs.models.ErrorContext;
import com.azure.messaging.eventhubs.models.EventContext;
import com.azure.messaging.eventhubs.models.PartitionContext;
import com.azure.storage.blob.BlobContainerAsyncClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

@RestController
public class EventHubSendController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHubSendController.class);
    @Value("${spring.cloud.azure.eventhubs.connection-string}")
    private String eventHubConnectionString;
    @Value("${spring.cloud.azure.storage.connection-string}")
    private String storageConnectionString;
    @Value("${spring.cloud.azure.storage.container-name}")
    private String storageContainerName;

    @GetMapping("/api/eventhub/send")
    public ResponseEntity<String> eventHubSendData() {
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(eventHubConnectionString)
                .buildProducerClient();

        List<EventData> allEvents = Arrays.asList(new EventData("Foo"), new EventData("Bar"));
        EventDataBatch eventDataBatch = producer.createBatch();

        for (EventData eventData : allEvents) {
            if (!eventDataBatch.tryAdd(eventData)) {

                producer.send(eventDataBatch);
                LOGGER.info("Sent message to Event Hub..");
                eventDataBatch = producer.createBatch();

                if (!eventDataBatch.tryAdd(eventData)) {
                    LOGGER.info("Sent prior excess messages to Event Hub..");
                    throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
                            + eventDataBatch.getMaxSizeInBytes());
                }
            }
        }
        if (eventDataBatch.getCount() > 0) {
            LOGGER.info("Sent message to Event Hub..");
            producer.send(eventDataBatch);
        }

        final Consumer<EventContext> PARTITION_PROCESSOR = eventContext -> {
            LOGGER.info("Processing events..");
            PartitionContext partitionContext = eventContext.getPartitionContext();
            EventData eventData = eventContext.getEventData();
        
            LOGGER.info("Processing event from partition {} with sequence number {} with body: {}\n",
                partitionContext.getPartitionId(), eventData.getSequenceNumber(), eventData.getBodyAsString());
        
            // Every 10 events received, it will update the checkpoint stored in Azure Blob Storage.
            if (eventData.getSequenceNumber() % 10 == 0) {
                eventContext.updateCheckpoint();
            }
        };
        
        final Consumer<ErrorContext> ERROR_HANDLER = errorContext -> {
            LOGGER.info("Error occurred in partition processor for partition {}, {}\n",
                errorContext.getPartitionContext().getPartitionId(),
                errorContext.getThrowable());
        };

        BlobContainerAsyncClient blobContainerAsyncClient = new BlobContainerClientBuilder()
                .connectionString(storageConnectionString)
                .containerName(storageContainerName)
                .buildAsyncClient();

        // Create a builder object that you will use later to build an event processor
        // client to receive and process events and errors.
        EventProcessorClientBuilder eventProcessorClientBuilder = new EventProcessorClientBuilder()
                .connectionString(eventHubConnectionString)
                .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                .processEvent(PARTITION_PROCESSOR)
                .processError(ERROR_HANDLER)
                .checkpointStore(new BlobCheckpointStore(blobContainerAsyncClient));

        // Use the builder object to create an event processor client
        EventProcessorClient eventProcessorClient = eventProcessorClientBuilder.buildEventProcessorClient();

        LOGGER.info("Starting event processor");
        eventProcessorClient.start();
        producer.close();

        return new ResponseEntity<String>("Sent message to Event Hub..", HttpStatus.OK);
    }
}
