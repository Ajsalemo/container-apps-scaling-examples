# container-apps-scaling-examples
Various examples of Azure Container Apps using KEDA scaling

The ARM template within the `deployment` folder of the following examples contains the scale rules for enabling KEDA scaling on Azure Container Apps. These contain runnable examples, as long as the associated resources are created (eg., Service Bus, Storage Queue, etc.)

```
| -- http
|   | -- deployment
|   |   |-- // Contains a HTTP trigger for scaling
| -- keda-azure-blob-storage
|   | -- deployment
|   |   |-- // Contains a KEDA Azure Blob Storage trigger
| -- keda-azure-service-bus
|   | -- deployment
|   |   |-- // Contains a KEDA Azure Service Bus trigger
| -- keda-azure-storage-queue
|   | -- deployment
|   |   |-- // Contains a KEDA Azure Storage Queue trigger
| -- keda-cpu
|   | -- deployment
|   |   |-- // Contains a KEDA CPU trigger
| -- keda-memory
|   | -- deployment
|   |   |-- // Contains a KEDA memory trigger
```

