const express = require('express');
const router = express.Router();
const db = require('../db');

router.post('/login', (req, res) => {
  const { username, password } = req.body;

  const query = 'SELECT * FROM users WHERE username = ? AND password = ?';
  db.query(query, [username, password], (err, results) => {
    if (err) {
      console.error('❌ Query Error:', err);
      return res.status(500).json({ success: false, message: "Server error" });
    }

    if (results.length > 0) {
      const user = results[0];
      res.json({
        success: true,
        message: "Login successful",
        user: {
          id: user.id,
          username: user.username,
          email: user.email,
          phone: user.phone,
          address: user.address
        }
      });
    } else {
      res.json({
        success: false,
        message: "Invalid username or password"
      });
    }
  });
});

module.exports = router;
