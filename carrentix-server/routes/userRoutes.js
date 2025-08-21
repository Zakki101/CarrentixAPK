const express = require('express');
const router = express.Router();
const db = require('../db'); 

router.post('/', (req, res) => {
    const { username, email, password, phone } = req.body;

    if (!username || !email || !password || !phone) {
        return res.status(400).json({ message: 'Missing fields' });
    }

    const query = 'INSERT INTO users (username, email, password, phone) VALUES (?, ?, ?, ?)';
    db.query(query, [username, email, password, phone], (err, result) => {
        if (err) {
            console.error('Error inserting user:', err);
            return res.status(500).json({ message: 'Server error' });
        }
        res.status(201).json({ message: 'User registered successfully' });
    });
});

router.get('/:id', (req, res) => {
    const userId = req.params.id;
    db.query('SELECT * FROM users WHERE id = ?', [userId], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ message: "Server error" });
        }

        if (results.length === 0) {
            return res.status(404).json({ message: "User not found" });
        }

        res.json(results[0]);
    });
});

router.put('/:id', (req, res) => {
    const userId = req.params.id;
    const { username, email, address, phone, password } = req.body;

    db.query(
        'UPDATE users SET username = ?, email = ?, address = ?, phone = ?, password = ? WHERE id = ?',
        [username, email, address, phone, password, userId],
        (err, result) => {
            if (err) {
                console.error(err);
                return res.status(500).json({ success: false, message: "Update failed" });
            }

            res.json({ success: true, message: "User updated successfully" });
        }
    );
});

router.delete('/:id', (req, res) => {
    const userId = req.params.id;
    db.query('DELETE FROM users WHERE id = ?', [userId], (err, result) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ success: false, message: "Delete failed" });
        }

        res.json({ success: true, message: "User deleted successfully" });
    });
});

module.exports = router;