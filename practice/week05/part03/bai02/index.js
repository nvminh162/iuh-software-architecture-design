const express = require("express");
const mongoose = require("mongoose");

const app = express();
app.use(express.json());

const itemSchema = new mongoose.Schema({
  name: { type: String, required: true },
  createdAt: { type: Date, default: Date.now },
});

const Item = mongoose.model("Item", itemSchema);

app.post("/items", async (req, res) => {
  const item = await Item.create({ name: req.body.name || "sample-item" });
  res.status(201).json(item);
});

app.get("/items", async (req, res) => {
  const items = await Item.find().sort({ createdAt: -1 });
  res.json(items);
});

app.get("/", (req, res) => {
  res.send("Node.js + MongoDB is running");
});

async function start() {
  await mongoose.connect(process.env.MONGO_URI || "mongodb://mongodb:27017/myapp");
  app.listen(3000, () => {
    console.log("Server is running on port 3000");
  });
}

start().catch((error) => {
  console.error(error);
  process.exit(1);
});
