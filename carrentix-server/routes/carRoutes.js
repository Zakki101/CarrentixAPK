const express = require('express');
const router = express.Router();
const db = require('../db');

router.get('/cars', (req, res) => {
    const query = 'SELECT * FROM cars';
    db.query(query, (err, results) => {
        if (err) {
            console.error('Error fetching cars:', err);
            return res.status(500).json({ message: 'Server error' });
        }
        res.json(results);
    });
});

router.get('/cars/:id', (req, res) => {
    const carId = req.params.id;
    const query = 'SELECT * FROM cars WHERE id = ?';
    
    db.query(query, [carId], (err, results) => {
        if (err) {
            console.error('Error fetching car by ID:', err);
            return res.status(500).json({ message: 'Server error' });
        }

        if (results.length === 0) {
            return res.status(404).json({ message: 'Car not found' });
        }

        res.json(results[0]); 
    });
});

module.exports = router;
