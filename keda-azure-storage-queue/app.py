from uuid import uuid4
from os import getenv
from dotenv import load_dotenv
from azure.storage.queue import QueueServiceClient
from azure.identity import DefaultAzureCredential
from flask import Flask

load_dotenv()

app = Flask(__name__)
token_credential = DefaultAzureCredential()
AZURE_STORAGE_ACCOUNT_NAME = getenv('AZURE_STORAGE_ACCOUNT_NAME')
AZURE_STORAGE_QUEUE_NAME = getenv('AZURE_STORAGE_QUEUE_NAME')

queue_service_client = QueueServiceClient(
        account_url=f"https://{AZURE_STORAGE_ACCOUNT_NAME}.queue.core.windows.net",
        credential=token_credential
    )

queue = queue_service_client.get_queue_client(AZURE_STORAGE_QUEUE_NAME)


@app.route("/")
def index():
    return {"msg": "container-apps-scaling-examples-keda-azure-storage-queue"}


@app.route("/api/queue/send")
def sendQueueMessage():
    message = f"queue-message-{uuid4()}"
    queue.send_message(message)
    return {"msg": f"Sent queue message: {message}"}


@app.route("/api/queue/clear")
def clearQueueMessages():
    queue.clear_messages()
    return {"msg": "Cleared messages in queue."}
