-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 31, 2026 at 04:30 PM
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
-- Database: `forward_chaining_tanaman_padi`
--

-- --------------------------------------------------------

--
-- Table structure for table `diagnoses`
--

CREATE TABLE `diagnoses` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `disease_id` bigint(20) UNSIGNED DEFAULT NULL,
  `diagnosis_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `diagnosis_details`
--

CREATE TABLE `diagnosis_details` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `diagnosis_id` bigint(20) UNSIGNED NOT NULL,
  `symptom_id` bigint(20) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `diseases`
--

CREATE TABLE `diseases` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `code` varchar(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `solution` text DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `diseases`
--

INSERT INTO `diseases` (`id`, `code`, `name`, `description`, `solution`, `created_at`, `updated_at`) VALUES
(1, 'P01', 'Hawar Daun Bakteri', 'Penyakit yang disebabkan oleh bakteri Xanthomonas oryzae.', 'Gunakan varietas tahan dan lakukan sanitasi lahan.', NULL, NULL),
(2, 'P02', 'Busuk Akar', 'Penyakit yang menyerang sistem perakaran tanaman.', 'Perbaiki drainase dan gunakan fungisida.', NULL, NULL),
(3, 'P03', 'Tungro', 'Penyakit virus yang ditularkan oleh wereng hijau.', 'Kendalikan wereng dan gunakan varietas tahan.', NULL, NULL),
(4, 'P04', 'Blast', 'Penyakit jamur Pyricularia oryzae.', 'Gunakan fungisida dan lakukan pemupukan berimbang.', NULL, NULL),
(6, 'P05', 'Daun Sakit', 'asdasdasasdasasd', 'asdasdasd', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `rules`
--

CREATE TABLE `rules` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `disease_id` bigint(20) UNSIGNED NOT NULL,
  `rule_name` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rules`
--

INSERT INTO `rules` (`id`, `disease_id`, `rule_name`, `created_at`, `updated_at`) VALUES
(1, 1, 'Rule P01', NULL, NULL),
(2, 2, 'Rule P02', NULL, NULL),
(3, 3, 'Rule P03', NULL, NULL),
(4, 4, 'Rule P04', NULL, NULL),
(6, 6, 'Aturan 5', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `rule_details`
--

CREATE TABLE `rule_details` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `rule_id` bigint(20) UNSIGNED NOT NULL,
  `symptom_id` bigint(20) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rule_details`
--

INSERT INTO `rule_details` (`id`, `rule_id`, `symptom_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(4, 2, 3),
(5, 2, 4),
(7, 3, 5),
(8, 3, 6),
(10, 4, 9),
(11, 4, 10),
(12, 6, 5),
(13, 6, 6);

-- --------------------------------------------------------

--
-- Table structure for table `symptoms`
--

CREATE TABLE `symptoms` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `code` varchar(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `symptoms`
--

INSERT INTO `symptoms` (`id`, `code`, `name`, `description`, `created_at`, `updated_at`) VALUES
(1, 'G01', 'Daun menguning', 'Daun berubah warna menjadi kuning', NULL, NULL),
(2, 'G02', 'Bercak coklat pada daun', 'Muncul bercak coklat pada permukaan daun', NULL, NULL),
(3, 'G03', 'Tanaman layu', 'Tanaman terlihat layu meskipun cukup air', NULL, NULL),
(4, 'G04', 'Akar membusuk', 'Akar berwarna coklat kehitaman dan membusuk', NULL, NULL),
(5, 'G05', 'Daun bergaris kuning', 'Daun memiliki garis-garis kuning', NULL, NULL),
(6, 'G06', 'Pertumbuhan tanaman terhambat', 'Tanaman tumbuh lebih lambat dari normal', NULL, NULL),
(7, 'G07', 'Daun menggulung', 'Daun menggulung ke dalam', NULL, NULL),
(8, 'G08', 'Batang membusuk', 'Batang lunak dan mengalami pembusukan', NULL, NULL),
(9, 'G09', 'Malai tidak terisi sempurna', 'Bulir padi tidak terisi penuh', NULL, NULL),
(10, 'G10', 'Bercak berbentuk belah ketupat', 'Bercak khas penyakit blast', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `alamat` text DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `alamat`, `username`, `password`, `email`) VALUES
(1, 'Administrator', 'Jalan Jabaah, Jakarta Timur', 'admin', 'admin', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `diagnoses`
--
ALTER TABLE `diagnoses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_diagnoses_disease` (`disease_id`);

--
-- Indexes for table `diagnosis_details`
--
ALTER TABLE `diagnosis_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_diagnosis_details_diagnosis` (`diagnosis_id`),
  ADD KEY `fk_diagnosis_details_symptom` (`symptom_id`);

--
-- Indexes for table `diseases`
--
ALTER TABLE `diseases`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `rules`
--
ALTER TABLE `rules`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_rules_disease` (`disease_id`);

--
-- Indexes for table `rule_details`
--
ALTER TABLE `rule_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_rule_details_rule` (`rule_id`),
  ADD KEY `fk_rule_details_symptom` (`symptom_id`);

--
-- Indexes for table `symptoms`
--
ALTER TABLE `symptoms`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `diagnoses`
--
ALTER TABLE `diagnoses`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `diagnosis_details`
--
ALTER TABLE `diagnosis_details`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `diseases`
--
ALTER TABLE `diseases`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `rules`
--
ALTER TABLE `rules`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `rule_details`
--
ALTER TABLE `rule_details`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `symptoms`
--
ALTER TABLE `symptoms`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `diagnoses`
--
ALTER TABLE `diagnoses`
  ADD CONSTRAINT `fk_diagnoses_disease` FOREIGN KEY (`disease_id`) REFERENCES `diseases` (`id`) ON DELETE SET NULL;

--
-- Constraints for table `diagnosis_details`
--
ALTER TABLE `diagnosis_details`
  ADD CONSTRAINT `fk_diagnosis_details_diagnosis` FOREIGN KEY (`diagnosis_id`) REFERENCES `diagnoses` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_diagnosis_details_symptom` FOREIGN KEY (`symptom_id`) REFERENCES `symptoms` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `rules`
--
ALTER TABLE `rules`
  ADD CONSTRAINT `fk_rules_disease` FOREIGN KEY (`disease_id`) REFERENCES `diseases` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `rule_details`
--
ALTER TABLE `rule_details`
  ADD CONSTRAINT `fk_rule_details_rule` FOREIGN KEY (`rule_id`) REFERENCES `rules` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_rule_details_symptom` FOREIGN KEY (`symptom_id`) REFERENCES `symptoms` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
