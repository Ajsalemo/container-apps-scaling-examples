# container-apps-scaling-examples
Various examples of Azure Container Apps using KEDA scaling

The ARM template within the `deployment` folder of the following examples contains the scale rules for enabling KEDA scaling on Azure Container Apps. These contain runnable examples, as long as the associated resources are created (eg., Service Bus, Storage Queue, etc.)

The `deployment` folder contains the following two files:
- `armdeploy.json` - A deployable ARM template with said scaling rules enabled.
- `arm-deploy-command.txt` - A file containing the command to deploy the example. Copy and paste this into your terminal when wanting to deploy the example. 

Ensure the `Dockerfile` is built and pushed to Azure Container Registry and all needed environment variables are set prior to deployment.

