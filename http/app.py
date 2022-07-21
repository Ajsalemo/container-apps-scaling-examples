import platform
from fastapi import FastAPI

app = FastAPI()


@app.get("/")
async def indec():
    return {"message": "container-apps-scaling-examples-http"}


@app.get("/api/os_info")
async def read_item():
    os = platform.system()
    return {"os_type": os}
