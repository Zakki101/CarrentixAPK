-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 28, 2025 at 12:10 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `carrentix`
--

-- --------------------------------------------------------

--
-- Table structure for table `cars`
--

CREATE TABLE `cars` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `colour` varchar(50) DEFAULT NULL,
  `passenger_capacity` int(11) DEFAULT NULL,
  `engine` varchar(100) DEFAULT NULL,
  `trim` varchar(50) DEFAULT NULL,
  `transmission` varchar(50) DEFAULT NULL,
  `fuel_type` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `picture_url` text DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cars`
--

INSERT INTO `cars` (`id`, `name`, `year`, `colour`, `passenger_capacity`, `engine`, `trim`, `transmission`, `fuel_type`, `description`, `price`, `picture_url`, `address`) VALUES
(1, 'Toyota Innova', 2022, 'Black', 7, '2.0 4 Cylinder', 'G', 'Automatic', 'Diesel', 'Toyota Innova adalah mobil MPV (Multi-Purpose Vehicle) yang dirancang untuk memberikan kenyamanan dan keandalan, baik untuk perjalanan keluarga maupun keperluan bisnis. Dengan kabin yang luas, fitur interior modern, serta mesin bertenaga dan efisien, Innova menawarkan pengalaman berkendara yang stabil dan nyaman. Desain eksteriornya yang elegan berpadu dengan teknologi keselamatan mutakhir, menjadikan Toyota Innova pilihan ideal bagi pengguna yang mengutamakan fungsionalitas dan kenyamanan dalam satu kendaraan.', 650000.00, 'http://10.0.2.2:3000/images/kijang.jpg', 'Jl. Raya Pondok Gede No. 88, Kel. Pinang Ranti'),
(2, 'Toyota Innova', 2022, 'Black', 7, '2.0 4 Cylinder', 'G', 'Automatic', 'Diesel', 'Toyota Innova adalah mobil MPV (Multi-Purpose Vehicle) yang dirancang untuk memberikan kenyamanan dan keandalan, baik untuk perjalanan keluarga maupun keperluan bisnis. Dengan kabin yang luas, fitur interior modern, serta mesin bertenaga dan efisien, Innova menawarkan pengalaman berkendara yang stabil dan nyaman. Desain eksteriornya yang elegan berpadu dengan teknologi keselamatan mutakhir, menjadikan Toyota Innova pilihan ideal bagi pengguna yang mengutamakan fungsionalitas dan kenyamanan dalam satu kendaraan.', 650000.00, 'http://10.0.2.2:3000/images/kijang.jpg', 'Jl. Raya Pondok Gede No. 88, Kel. Pinang Ranti'),
(3, 'Toyota Avanza', 2021, 'SIlver', 7, '1.3 4 Cylinder', 'E', 'Manual', 'Petrol ', 'Toyota Avanza adalah mobil MPV kompak yang dikenal luas di Indonesia karena kepraktisan, efisiensi, dan harga yang terjangkau. Dirancang untuk memenuhi kebutuhan keluarga maupun operasional sehari-hari, Avanza menawarkan kabin yang lapang untuk hingga tujuh penumpang, serta fitur-fitur fungsional yang mendukung kenyamanan dan keselamatan berkendara. Dengan mesin yang irit bahan bakar dan perawatan yang mudah, Toyota Avanza menjadi salah satu pilihan favorit di segmen kendaraan keluarga dan transportasi umum.', 398000.00, 'http://10.0.2.2:3000/images/avanza.png', 'Jl. Rawamangun Muka No. 11, Kel. Rawamangun'),
(4, 'Toyota Veloz', 2023, 'Hitam', 7, '1.5 4 Cylinder', 'Q', 'Automatic', 'Petrol ', 'Toyota Veloz adalah MPV premium yang merupakan versi lebih mewah dari Toyota Avanza, dirancang untuk memberikan kenyamanan dan gaya yang lebih modern bagi penggunanya. Dilengkapi dengan desain eksterior yang sporty dan elegan, serta interior yang lebih mewah dan lapang, Veloz menghadirkan pengalaman berkendara yang lebih menyenangkan. Fitur-fitur canggih seperti head unit layar sentuh, sistem keselamatan Toyota Safety Sense (di varian tertentu), dan kenyamanan suspensi yang ditingkatkan menjadikan Toyota Veloz pilihan ideal bagi keluarga modern yang menginginkan kendaraan stylish, fungsional, dan aman.\r\n', 485000.00, 'http://10.0.2.2:3000/images/veloz.jpg', 'Jl. Palmerah Barat No. 7, Kel. Palmerah'),
(5, 'Toyota Calya', 2023, 'Oranye', 7, '1.2 4 Cylinder', 'G', 'Manual', 'Petrol ', 'Toyota Calya adalah mobil MPV berkapasitas tujuh penumpang yang dirancang untuk keluarga dengan harga terjangkau dan konsumsi bahan bakar yang irit. Mobil ini memiliki desain kompak namun tetap lega di bagian dalam, cocok untuk penggunaan harian maupun perjalanan luar kota. Dilengkapi fitur-fitur dasar seperti AC, sistem audio, airbag, dan rem ABS, Toyota Calya menawarkan kenyamanan dan keamanan yang cukup baik untuk kelas mobil LCGC (Low Cost Green Car). Cocok sebagai mobil pertama bagi keluarga muda atau pengguna yang mencari kendaraan fungsional dan hemat.', 320000.00, 'http://10.0.2.2:3000/images/calya.jpg', 'Jl. Tebet Timur Dalam IV No. 34, Kel. Tebet Timur'),
(6, 'Toyota Calya', 2020, 'Hitam', 7, '1.2 4 Cylinder', 'G', 'Automatic', 'Petrol ', 'Toyota Calya adalah mobil MPV berkapasitas tujuh penumpang yang dirancang untuk keluarga dengan harga terjangkau dan konsumsi bahan bakar yang irit. Mobil ini memiliki desain kompak namun tetap lega di bagian dalam, cocok untuk penggunaan harian maupun perjalanan luar kota. Dilengkapi fitur-fitur dasar seperti AC, sistem audio, airbag, dan rem ABS, Toyota Calya menawarkan kenyamanan dan keamanan yang cukup baik untuk kelas mobil LCGC (Low Cost Green Car). Cocok sebagai mobil pertama bagi keluarga muda atau pengguna yang mencari kendaraan fungsional dan hemat.', 320000.00, 'http://10.0.2.2:3000/images/calya2.jpg', 'Jl. Tebet Timur Dalam IV No. 34, Kel. Tebet Timur');

-- --------------------------------------------------------

--
-- Stand-in structure for view `historyview`
-- (See below for the actual view)
--
CREATE TABLE `historyview` (
`payId` int(11)
,`rentedId` int(11)
,`userId` int(11)
,`carId` int(11)
,`name` varchar(100)
,`end_date` datetime
,`status` enum('RETURNED','WITHIN RENTAL PERIOD')
,`price` decimal(11,2)
,`picture_url` text
);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` int(11) NOT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `rentedcar_id` int(11) DEFAULT NULL,
  `price` decimal(11,2) DEFAULT NULL,
  `insurance_fee` decimal(11,2) DEFAULT NULL,
  `total` decimal(11,2) DEFAULT NULL,
  `id_photo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `payment_method`, `rentedcar_id`, `price`, `insurance_fee`, `total`, `id_photo`) VALUES
(1, 'DANA', 1, 398000.00, 100000.00, 498000.00, 'http://10.0.2.2:3000/images/avanza.png'),
(2, 'DANA', 2, 1300000.00, 100000.00, 1400000.00, 'http://10.0.2.2:3000/images/kijang.jpg'),
(3, 'OVO', 3, 970000.00, 100000.00, 1070000.00, '1751104831317-images.jpeg');

-- --------------------------------------------------------

--
-- Table structure for table `rentedcars`
--

CREATE TABLE `rentedcars` (
  `id` int(11) NOT NULL,
  `car_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `insurance_option` enum('WITH INSURANCE','NO INSURANCE') NOT NULL DEFAULT 'WITH INSURANCE',
  `renter_name` varchar(100) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `address` text NOT NULL,
  `status` enum('RETURNED','WITHIN RENTAL PERIOD') DEFAULT 'WITHIN RENTAL PERIOD'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rentedcars`
--

INSERT INTO `rentedcars` (`id`, `car_id`, `user_id`, `start_date`, `end_date`, `insurance_option`, `renter_name`, `phone_number`, `address`, `status`) VALUES
(1, 3, 1, '2024-06-03 16:33:55', '2024-06-04 16:33:55', 'WITH INSURANCE', 'Zuhairil Abid', '087820380383', 'Komp. Joglo Baru Blok I/27 ', 'RETURNED'),
(2, 1, 1, '2025-06-28 20:00:00', '2024-06-30 20:00:00', 'WITH INSURANCE', 'Zuhairil Abid', '087820380383', 'Komp. Joglo Baru Blok I/27 ', 'WITHIN RENTAL PERIOD'),
(3, 4, 1, '2025-07-01 16:58:54', '2025-07-03 16:59:01', 'WITH INSURANCE', 'Zuhairil Abid', '087820380383', 'Komp. Joglo Baru Block I/27', 'WITHIN RENTAL PERIOD');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `phone`, `address`) VALUES
(1, 'zuhairil', '12345678', '2310511112@mahasiswa.upnvj.ac.id', '087820380323', 'Joglo Baru Blok I/27');

-- --------------------------------------------------------

--
-- Structure for view `historyview`
--
DROP TABLE IF EXISTS `historyview`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `historyview`  AS SELECT `payments`.`id` AS `payId`, `rentedcars`.`id` AS `rentedId`, `users`.`id` AS `userId`, `cars`.`id` AS `carId`, `cars`.`name` AS `name`, `rentedcars`.`end_date` AS `end_date`, `rentedcars`.`status` AS `status`, `payments`.`total` AS `price`, `cars`.`picture_url` AS `picture_url` FROM (((`rentedcars` join `payments` on(`rentedcars`.`id` = `payments`.`rentedcar_id`)) join `cars` on(`cars`.`id` = `rentedcars`.`car_id`)) join `users` on(`users`.`id` = `rentedcars`.`user_id`)) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `rentedcar_id` (`rentedcar_id`);

--
-- Indexes for table `rentedcars`
--
ALTER TABLE `rentedcars`
  ADD PRIMARY KEY (`id`),
  ADD KEY `car_id` (`car_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cars`
--
ALTER TABLE `cars`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `rentedcars`
--
ALTER TABLE `rentedcars`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`rentedcar_id`) REFERENCES `rentedcars` (`id`);

--
-- Constraints for table `rentedcars`
--
ALTER TABLE `rentedcars`
  ADD CONSTRAINT `rentedcars_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `rentedcars_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
