const express = require('express');
const router = express.Router();
const db = require('../db'); 

router.get('/', (req, res) => {
    const sql = 'SELECT * FROM historyview';

    db.query(sql, (err, results) => {
        if (err) {
            console.error('Error fetching history view:', err);
            return res.status(500).json({ error: 'Database error' });
        }
        res.json(results);
    });
});

module.exports = router;
