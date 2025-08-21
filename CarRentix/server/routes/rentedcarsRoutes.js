const express = require('express');
const router = express.Router();
const db = require('../db'); 

router.post('/rentedcars', (req, res) => {
    const {
        car_id, user_id, start_date, end_date,
        insurance_option, renter_name, phone_number, address, status
    } = req.body;

    const sql = `
        INSERT INTO rentedcars (
            car_id, user_id, start_date, end_date,
            insurance_option, renter_name, phone_number, address, status
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
    `;

    db.query(sql, [
        car_id, user_id, start_date, end_date,
        insurance_option, renter_name, phone_number, address, status
    ], (err, result) => {
        if (err) {
            console.error('Error inserting rent request:', err);
            return res.status(500).json({ error: 'Failed to create rent request' });
        }
        
        res.status(201).json({ message: 'Rent request created', 
            id: result.insertId,
            car_id,
            user_id,
            start_date,
            end_date,
            insurance_option,
            renter_name,
            phone_number,
            address,
            status });
    });
});

module.exports = router;
