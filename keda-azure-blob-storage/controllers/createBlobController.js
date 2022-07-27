import express from "express";
import { v4 as uuidv4 } from "uuid";
import { DefaultAzureCredential } from "@azure/identity";
import { BlobServiceClient } from "@azure/storage-blob";

const router = express.Router();

const account = process.env.AZURE_STORAGE_ACCOUNT_NAME;
const containerName = process.env.AZURE_STORAGE_CONTAINER_NAME;
const defaultAzureCredential = new DefaultAzureCredential();

const blobServiceClient = new BlobServiceClient(
  `https://${account}.blob.core.windows.net`,
  defaultAzureCredential
);

export const createBlobController = router.get("/", async (_req, res) => {
  try {
    const containerClient = blobServiceClient.getContainerClient(containerName);
    const content = `order_${uuidv4}`;
    const blobName = `blob_${new Date().getTime()}`;
    const blockBlobClient = containerClient.getBlockBlobClient(blobName);
    const uploadBlobResponse = await blockBlobClient.upload(
      content,
      content.length
    );
    console.log(
      `Upload block blob ${blobName} successfully`,
      uploadBlobResponse.requestId
    );
    res.json({ msg: `Added new blob: ${blobName}` });
  } catch (error) {
    console.error(error);
  }
});
