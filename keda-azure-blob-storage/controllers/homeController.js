import express from "express"

const router = express.Router()

export const homeController = router.get("/", (_, res) => {
    try {
        res.json({ msg: "container-apps-scaling-examples-keda-blog-storage"})
    } catch (error) {
        console.error(error)
    }
})