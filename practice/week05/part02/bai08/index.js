const express = require("express");
const mysql = require("mysql2/promise");

const app = express();
const PORT = 3000;

const config = {
  host: process.env.DB_HOST || "mysql",
  port: Number(process.env.DB_PORT || 3306),
  user: process.env.DB_USER || "user",
  password: process.env.DB_PASSWORD || "password",
  database: process.env.DB_NAME || "mydb",
};

let pool;

async function initDb(retries = 10) {
  for (let i = 0; i < retries; i += 1) {
    try {
      pool = mysql.createPool(config);
      await pool.query("SELECT 1");
      console.log("Connected to MySQL");
      return;
    } catch (error) {
      console.log(`Waiting for MySQL... (${i + 1}/${retries})`);
      await new Promise((resolve) => setTimeout(resolve, 3000));
    }
  }
  throw new Error("Could not connect to MySQL");
}

app.get("/", async (req, res) => {
  const [rows] = await pool.query("SELECT NOW() AS now_time");
  res.json({ message: "Node.js connected to MySQL", mysql_time: rows[0].now_time });
});

initDb()
  .then(() => {
    app.listen(PORT, () => {
      console.log(`Server is running on port ${PORT}`);
    });
  })
  .catch((error) => {
    console.error(error.message);
    process.exit(1);
  });
