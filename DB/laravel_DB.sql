-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 01, 2023 at 03:59 PM
-- Server version: 8.0.32-0ubuntu0.22.04.2
-- PHP Version: 8.1.2-1ubuntu2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `laravel_DB`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories_tbls`
--

CREATE TABLE `categories_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `root_cat_id` int NOT NULL,
  `icon_address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `datetime` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `castom_link` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `visible` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `delete_flag` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `city_tbls`
--

CREATE TABLE `city_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `area` int NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `datetime` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `city_tbls`
--

INSERT INTO `city_tbls` (`id`, `name`, `area`, `status`, `datetime`) VALUES
(1, 'تهران', 0, '1', '1400');

-- --------------------------------------------------------

--
-- Table structure for table `discount_code_tbls`
--

CREATE TABLE `discount_code_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `count` int NOT NULL,
  `delete_flag` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `file_tbls`
--

CREATE TABLE `file_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `file_name` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_address` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_address_server` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_type` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_extention` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_size` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_hash` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_date_time` longtext COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `file_tbls`
--

INSERT INTO `file_tbls` (`id`, `file_name`, `file_address`, `file_address_server`, `file_type`, `file_extention`, `file_size`, `file_hash`, `file_date_time`) VALUES
(1, 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2', 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2', 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2', 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2', 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2', 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2', 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2', 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2');

-- --------------------------------------------------------

--
-- Table structure for table `hospital_img_tbls`
--

CREATE TABLE `hospital_img_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `hospital_id` int UNSIGNED NOT NULL,
  `file_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `hospital_tbls`
--

CREATE TABLE `hospital_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bio` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lat` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lng` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `start_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `end_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tel` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `city_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int UNSIGNED NOT NULL,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2019_12_14_000001_create_personal_access_tokens_table', 1),
(2, '2021_11_21_161718_create_users_tbls_table', 1),
(3, '2021_11_21_162021_create_notifications_tbls_table', 1),
(4, '2021_11_21_163847_create_report_tbls_table', 1),
(5, '2021_11_21_163905_create_reports_tbls_table', 1),
(6, '2021_11_21_163940_create_userstype_tbls_table', 1),
(7, '2021_11_25_161033_create_user_verify_tbls_table', 1),
(8, '2022_01_04_072748_create_file_tbls_table', 1),
(9, '2022_09_04_095553_create_categories_tbls_table', 1),
(10, '2022_09_04_115725_create_posts_tbls_table', 1),
(11, '2023_02_05_121847_create_settings_tbls_table', 1),
(12, '2023_02_10_112729_create_city_tbls_table', 1),
(13, '2023_02_12_103504_create_hospital_tbls_table', 1),
(14, '2023_02_12_174338_create_hospital_img_tbls_table', 1),
(15, '2023_02_18_095640_create_store_categories_tbls_table', 1),
(16, '2023_02_18_101220_create_store_tbls_table', 1),
(17, '2023_02_21_121843_create_discount_code_tbls_table', 1),
(18, '2023_02_21_132924_create_store_images_tbls_table', 1),
(19, '2023_02_22_154125_create_orders_tbls_table', 1),
(20, '2023_02_22_162157_create_transcation_tbls_table', 1),
(21, '2023_02_22_185840_create_visite_tbls_table', 1);

-- --------------------------------------------------------

--
-- Table structure for table `notifications_tbls`
--

CREATE TABLE `notifications_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `link` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `from` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `seen` tinyint(1) NOT NULL,
  `user_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders_tbls`
--

CREATE TABLE `orders_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `store_id` int UNSIGNED NOT NULL,
  `user_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tokenable_id` bigint UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `abilities` text COLLATE utf8mb4_unicode_ci,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `posts_tbls`
--

CREATE TABLE `posts_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `delete_flag` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `visible` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int UNSIGNED NOT NULL,
  `category_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reports_tbls`
--

CREATE TABLE `reports_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `condition` tinyint(1) NOT NULL,
  `report_id` int UNSIGNED NOT NULL,
  `user_id` int NOT NULL,
  `post_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `report_tbls`
--

CREATE TABLE `report_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `delete_flag` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `settings_tbls`
--

CREATE TABLE `settings_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `parametrs` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `discountall` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `store_categories_tbls`
--

CREATE TABLE `store_categories_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `root_cat_id` int NOT NULL,
  `icon_address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `datetime` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `castom_link` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `visible` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `delete_flag` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `store_images_tbls`
--

CREATE TABLE `store_images_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `store_id` int UNSIGNED NOT NULL,
  `file_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `store_tbls`
--

CREATE TABLE `store_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `delete_flag` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `visible` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `json` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `discount` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `adiscount` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int UNSIGNED NOT NULL,
  `category_id` int UNSIGNED NOT NULL,
  `file_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `transcation_tbls`
--

CREATE TABLE `transcation_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `value` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_id` int UNSIGNED NOT NULL,
  `user_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `userstype_tbls`
--

CREATE TABLE `userstype_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `en_title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `userstype_tbls`
--

INSERT INTO `userstype_tbls` (`id`, `title`, `en_title`) VALUES
(1, 'مدیر سیستم', 'ADMIN'),
(2, 'کاربر', 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `users_tbls`
--

CREATE TABLE `users_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `family` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bio` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `login_token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date_time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `website` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `times` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lat` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lng` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `google_auth` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `profile_image_file_id` int UNSIGNED NOT NULL,
  `city_id` int UNSIGNED NOT NULL,
  `user_type_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users_tbls`
--

INSERT INTO `users_tbls` (`id`, `username`, `password`, `name`, `family`, `phone`, `email`, `bio`, `login_token`, `date_time`, `user_type`, `status`, `website`, `address`, `times`, `lat`, `lng`, `google_auth`, `profile_image_file_id`, `city_id`, `user_type_id`) VALUES
(1, 'rezafta', 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2', 'رضا', 'فرازی', '09386949300', 'rezafta@outlook.com', 'rezafta', 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2', '1400', 'ADMIN', '1', 'sorapp.ir', 'sorapp.ir', '', '', '', '', 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_verify_tbls`
--

CREATE TABLE `user_verify_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `hash_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `visite_tbls`
--

CREATE TABLE `visite_tbls` (
  `id` bigint UNSIGNED NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int UNSIGNED NOT NULL,
  `doctor_id` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories_tbls`
--
ALTER TABLE `categories_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `city_tbls`
--
ALTER TABLE `city_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `discount_code_tbls`
--
ALTER TABLE `discount_code_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `file_tbls`
--
ALTER TABLE `file_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hospital_img_tbls`
--
ALTER TABLE `hospital_img_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hospital_tbls`
--
ALTER TABLE `hospital_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notifications_tbls`
--
ALTER TABLE `notifications_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders_tbls`
--
ALTER TABLE `orders_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`);

--
-- Indexes for table `posts_tbls`
--
ALTER TABLE `posts_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reports_tbls`
--
ALTER TABLE `reports_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `report_tbls`
--
ALTER TABLE `report_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `settings_tbls`
--
ALTER TABLE `settings_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `store_categories_tbls`
--
ALTER TABLE `store_categories_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `store_images_tbls`
--
ALTER TABLE `store_images_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `store_tbls`
--
ALTER TABLE `store_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transcation_tbls`
--
ALTER TABLE `transcation_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `userstype_tbls`
--
ALTER TABLE `userstype_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users_tbls`
--
ALTER TABLE `users_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_verify_tbls`
--
ALTER TABLE `user_verify_tbls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `visite_tbls`
--
ALTER TABLE `visite_tbls`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories_tbls`
--
ALTER TABLE `categories_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `city_tbls`
--
ALTER TABLE `city_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `discount_code_tbls`
--
ALTER TABLE `discount_code_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `file_tbls`
--
ALTER TABLE `file_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `hospital_img_tbls`
--
ALTER TABLE `hospital_img_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hospital_tbls`
--
ALTER TABLE `hospital_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `notifications_tbls`
--
ALTER TABLE `notifications_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders_tbls`
--
ALTER TABLE `orders_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `posts_tbls`
--
ALTER TABLE `posts_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reports_tbls`
--
ALTER TABLE `reports_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `report_tbls`
--
ALTER TABLE `report_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `settings_tbls`
--
ALTER TABLE `settings_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `store_categories_tbls`
--
ALTER TABLE `store_categories_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `store_images_tbls`
--
ALTER TABLE `store_images_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `store_tbls`
--
ALTER TABLE `store_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `transcation_tbls`
--
ALTER TABLE `transcation_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `userstype_tbls`
--
ALTER TABLE `userstype_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users_tbls`
--
ALTER TABLE `users_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user_verify_tbls`
--
ALTER TABLE `user_verify_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `visite_tbls`
--
ALTER TABLE `visite_tbls`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
