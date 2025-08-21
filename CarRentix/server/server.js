const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const authRoutes = require('./routes/auth');
const userRoutes = require('./routes/userRoutes');
const carRoutes = require('./routes/carRoutes');
const rentedcarsRoutes = require('./routes/rentedcarsRoutes');
const paymentRoutes = require('./routes/paymentRoutes');
const historyRoutes = require('./routes/historyRoutes');

const app = express();
const PORT = 3000;

app.use(cors());
app.use(bodyParser.json());
app.use('/api', authRoutes);
app.use('/api/users', userRoutes);
app.use('/api', carRoutes); 
app.use('/api', rentedcarsRoutes)
app.use('/api', paymentRoutes);
app.use('/api/history', historyRoutes);
app.use('/images', express.static('images'));

app.listen(PORT, () => {
  console.log(`Server is running at http://localhost:${PORT}`);
});
