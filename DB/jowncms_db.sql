-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Aug 08, 2022 at 03:27 PM
-- Server version: 8.0.21
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jowncms_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories_tbl`
--

DROP TABLE IF EXISTS `categories_tbl`;
CREATE TABLE IF NOT EXISTS `categories_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `icon_address` varchar(255) COLLATE utf8_lithuanian_ci NOT NULL,
  `type` varchar(255) COLLATE utf8_lithuanian_ci NOT NULL,
  `create_date` varchar(255) COLLATE utf8_lithuanian_ci NOT NULL,
  `enabled_flag` varchar(255) COLLATE utf8_lithuanian_ci NOT NULL,
  `root_id` int NOT NULL,
  `user_id` int NOT NULL,
  `cat_link` varchar(255) COLLATE utf8_lithuanian_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_lithuanian_ci;

--
-- Dumping data for table `categories_tbl`
--

INSERT INTO `categories_tbl` (`id`, `title`, `icon_address`, `type`, `create_date`, `enabled_flag`, `root_id`, `user_id`, `cat_link`) VALUES
(7, 'تست ویرایش', '', 'Sub', '2022/08/07 16:11:36', '2', 5, 1, '');

-- --------------------------------------------------------

--
-- Table structure for table `configs_tbl`
--

DROP TABLE IF EXISTS `configs_tbl`;
CREATE TABLE IF NOT EXISTS `configs_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `configs_json` text COLLATE utf8_lithuanian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_lithuanian_ci;

--
-- Dumping data for table `configs_tbl`
--

INSERT INTO `configs_tbl` (`id`, `configs_json`) VALUES
(1, '{}');

-- --------------------------------------------------------

--
-- Table structure for table `posts_tbl`
--

DROP TABLE IF EXISTS `posts_tbl`;
CREATE TABLE IF NOT EXISTS `posts_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_lithuanian_ci NOT NULL,
  `content` text COLLATE utf8_lithuanian_ci NOT NULL,
  `enabled_flag` varchar(1) COLLATE utf8_lithuanian_ci NOT NULL,
  `edit_time` varchar(50) COLLATE utf8_lithuanian_ci NOT NULL,
  `user_id` int NOT NULL,
  `category_id` int NOT NULL,
  `image_address` varchar(1024) COLLATE utf8_lithuanian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_lithuanian_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users_tbl`
--

DROP TABLE IF EXISTS `users_tbl`;
CREATE TABLE IF NOT EXISTS `users_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `family` varchar(100) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `password` varchar(512) CHARACTER SET utf16 COLLATE utf16_persian_ci NOT NULL,
  `login_token` varchar(512) CHARACTER SET utf32 COLLATE utf32_persian_ci NOT NULL,
  `google2f` varchar(512) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `user_type` varchar(25) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `active_user_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `create_datetime` varchar(64) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `email` varchar(512) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `address` varchar(1024) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `city` varchar(512) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_lithuanian_ci;

--
-- Dumping data for table `users_tbl`
--

INSERT INTO `users_tbl` (`id`, `name`, `family`, `username`, `password`, `login_token`, `google2f`, `user_type`, `active_user_flag`, `create_datetime`, `email`, `address`, `city`, `phone`) VALUES
(1, 'Reza', 'Farazi', 'rezafta', 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2', 'd0f1fa128cee4a68c57bec9305fa28da48e6fec3846e2a0c68d980ca059b13e2', '24J4II7O4EKBDJX4', 'ADMIN', '1', '1400', 'rezafta@outlook.com', 'Tehran', 'Tehran', '09932017038');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
