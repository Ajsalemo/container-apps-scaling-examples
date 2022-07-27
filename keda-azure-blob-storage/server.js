import 'dotenv/config'
import express from "express";
import { homeController } from "./controllers/homeController.js";
import { createBlobController } from "./controllers/createBlobController.js";

const app = express();
const port = process.env.PORT || 3000;

app.use("/", homeController);
app.use("/api/blob/create", createBlobController);

app.listen(port, () => console.log(`Listening on port: ${port}`));
