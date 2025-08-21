const express = require('express');
const router = express.Router();
const multer = require('multer');
const path = require('path');
const db = require('../db');

// Setup multer for file uploads
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'uploads/id_photos');
    },
    filename: (req, file, cb) => {
        const uniqueName = Date.now() + '-' + file.originalname;
        cb(null, uniqueName);
    }
});

const upload = multer({ storage: storage });

router.post('/payments', upload.single('id_photo'), (req, res) => {
    const { payment_method, rentedcar_id, price, insurance_fee, total } = req.body;
    const id_photo = req.file ? req.file.filename : null;

    if (!payment_method || !rentedcar_id || !price || !id_photo || !total) {
        return res.status(400).json({ error: 'Missing required fields' });
    }

    const sql = `
        INSERT INTO payments (
            payment_method, rentedcar_id, price, insurance_fee, id_photo, total
        ) VALUES (?, ?, ?, ?, ?, ?)
    `;

    db.query(sql, [payment_method, rentedcar_id, price, insurance_fee, id_photo, total], (err, result) => {
        if (err) {
            console.error('Error inserting payment:', err);
            return res.status(500).json({ error: 'Failed to insert payment' });
        }

        res.status(201).json({ message: 'Payment recorded successfully', payment_id: result.insertId });
    });
});

module.exports = router;
