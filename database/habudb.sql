-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 05, 2021 at 11:24 AM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `habudb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_info`
--

CREATE TABLE `admin_info` (
  `email` varchar(30) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin_info`
--

INSERT INTO `admin_info` (`email`, `name`, `password`) VALUES
('admin', 'Bishop', 'Aa111111');

-- --------------------------------------------------------

--
-- Table structure for table `bannedusers`
--

CREATE TABLE `bannedusers` (
  `id` int(30) NOT NULL,
  `email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `email` varchar(30) NOT NULL,
  `product` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`id`, `email`, `product`) VALUES
(22, 'a', 15);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `image` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`, `image`) VALUES
(1, 'Clothes', 'clothes.png'),
(2, 'Furniture', 'furniture.png'),
(3, 'Accessories', 'accessories.png'),
(8, 'Beuty', 'beuty.png'),
(9, 'Toys', 'toys.png'),
(10, 'TVs', 'tvs.png'),
(11, 'Kitchen', 'kitchen.png'),
(13, 'Washing Machine', 'icons8-washing-machine-80.png'),
(16, 'Garbage Cat', '5882614973Garbage Cat.png'),
(20, 'adasdadad', '2392647753adasdadad.png');

-- --------------------------------------------------------

--
-- Table structure for table `customer_info`
--

CREATE TABLE `customer_info` (
  `email` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `name` varchar(20) NOT NULL,
  `location` varchar(20) NOT NULL,
  `token` varchar(5) NOT NULL,
  `isverified` tinyint(1) NOT NULL,
  `CreationDate` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer_info`
--

INSERT INTO `customer_info` (`email`, `password`, `name`, `location`, `token`, `isverified`, `CreationDate`) VALUES
('1', '2', 'baboshka', '4', '5', 1, ''),
('3', '2', '3', '4', '5', 1, ''),
('71730712@students.liu.edu.lb', 'Aa111111', 'adadadad', 'Adadasdasd', '75846', 1, '05/06/2021'),
('a', 'a', 'a', 'a', 'a', 1, '02/3/1222'),
('aaa@aa.cp', 'aA111111', 'adadad', 'aaa', '82951', 0, '14/04/2021'),
('Aasad@adasd.co', 'Ad111111', 'adadasd', 'adadadads', '19868', 0, '03/06/2021'),
('adad@dasdaa.co', 'Aa111111', 'adadasdsad', 'Tar', '96951', 0, '03/06/2021'),
('adawaad@dad.co', 'Aa111111', 'ada', 'adw', '86600', 0, '04/05/2021'),
('adawdad@dascad.co', 'Aa111111', 'adasdasd', 'Tar', '19750', 1, '02/06/2021'),
('adwda', 'adad', 'dd', 'ad', '15525', 0, '04/05/2021'),
('fireoftheeagle@gmail.com', 'Aa111111', 'Test', 'Ter', '49121', 1, '04/05/2021'),
('ss', 'dd', 'aa', 'dd', '60799', 0, '04/05/2021');

-- --------------------------------------------------------

--
-- Table structure for table `customer_orders`
--

CREATE TABLE `customer_orders` (
  `id` int(11) NOT NULL,
  `pname` varchar(50) NOT NULL,
  `customer_email` varchar(30) NOT NULL,
  `province` varchar(30) NOT NULL,
  `city` varchar(30) NOT NULL,
  `street` varchar(30) NOT NULL,
  `progress` varchar(30) NOT NULL,
  `price` int(10) NOT NULL,
  `date` varchar(20) NOT NULL,
  `recipient` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer_orders`
--

INSERT INTO `customer_orders` (`id`, `pname`, `customer_email`, `province`, `city`, `street`, `progress`, `price`, `date`, `recipient`) VALUES
(9, 'Training Hoodie Army Green Men', 'a', 'Jnoub', 'Tyre', '23 = s', 'not delivered', 50, '04/05/2021', '212734863478023-ih'),
(10, 'Training Hoodie Army Green Men', 'a', 'Sss', 'ree', 'ww', 'not delivered', 50, '04/05/2021', '89209778402700-th'),
(11, 'Training Hoodie Army Green Men', 'fireoftheeagle@gmail.com', 'Jnoub', 'Tyre', '23, near al jawad', 'not delivered', 50, '04/05/2021', '834753414037752-sf'),
(12, 'Grey Hoodie Men - Limited Edition', 'a', 'tes', 'as', 'dda', 'not delivered', 99, '07/05/2021', '331802420808725-if'),
(13, 'Grey Hoodie Men - Limited Edition', 'a', 'aa', 's', 'w', 'not delivered', 99, '07/05/2021', '721076907986397-jx'),
(14, 'Grey Hoodie Men - Limited Edition', 'a', 'ss', 'dd', 'a', 'not delivered', 99, '07/05/2021', '41581697781335-vq'),
(15, 'Grey Hoodie Men - Limited Edition', 'a', '', '', '', 'not delivered', 99, '07/05/2021', '322318538156199-wj'),
(16, 'Grey Hoodie Men - Limited Edition', 'a', 'dddass', 'aa', 'sss', 'not delivered', 99, '07/05/2021', '651815146103332-rd'),
(17, 'Grey Hoodie Men - Limited Edition', 'a', 'aa', 'ss', 'ddd', 'not delivered', 99, '07/05/2021', '990013601862962-fs'),
(18, 'Training Hoodie Army Green Men', 'adawdad@dascad.co', 'Tyre', 'Sa', 'Va1', 'not delivered', 50, '02/06/2021', '38303820372990-ht');

-- --------------------------------------------------------

--
-- Table structure for table `deleted_products`
--

CREATE TABLE `deleted_products` (
  `id` int(11) NOT NULL,
  `item_id` varchar(30) NOT NULL,
  `item_name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `token` varchar(20) NOT NULL,
  `title` varchar(30) NOT NULL,
  `text` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`id`, `token`, `title`, `text`) VALUES
(2, '5908148', 'Test', 'Daafff');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `weight` varchar(50) NOT NULL,
  `size` varchar(50) NOT NULL,
  `category` int(11) NOT NULL,
  `image` varchar(80) NOT NULL,
  `price` int(50) NOT NULL,
  `stock` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `weight`, `size`, `category`, `image`, `price`, `stock`) VALUES
(4, 'Yellow Shirt', '0.1', 'XL', 1, 'yellowshirt.jpg', 36, 8),
(7, 'Black Shirt', '0.05', 'M', 1, '7405269261.hmgoepprod.jpg', 12, 23),
(9, 'Training Hoodie Army Green Men', '0.3', 'L', 1, '1290257012gray.jpg', 50, 36),
(10, 'Grey Hoodie Men - Limited Edition', '0.3', 'M', 1, '3205788670KN67308000_GY25_893dfa57-3b96-4e6e-88cc-', 99, 0),
(15, 'Elephant Painting', '99', 'XLLL', 3, '997186157.png', 10, 50);

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `id` int(11) NOT NULL,
  `email` varchar(30) NOT NULL,
  `title` varchar(30) NOT NULL,
  `text` text NOT NULL,
  `date` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`id`, `email`, `title`, `text`, `date`) VALUES
(3, 'a', 'adawdad', 'awdwadwadadadadwd', '25/04/2021'),
(6, 'adawdad@dascad.co', 'Test', 'Asad as dsad asd asd asd asd asd asd asdasdaw da fae fewsr fergfsdf sd fsdf sd fsdf sd fsdf sdf sdf sd fsdf sdf sdf sdf dsf ds fsdf sd fsdf sd fsdf ', '02/06/2021');

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `productid` int(11) NOT NULL,
  `email` varchar(30) NOT NULL,
  `title` varchar(30) NOT NULL,
  `rate` int(1) NOT NULL,
  `text` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`id`, `productid`, `email`, `title`, `rate`, `text`) VALUES
(1, 9, 'a', 'Not good', 3, 'I hate it here'),
(3, 9, '1', 'Bla Bla', 5, 'Lorem absolem bla bla bla bla bla adjaopdjawdjawopdk pawok poawkdpaow jaoipchap jedpaw dkpawokdpawok dpoawjdcp ajwp napcxpwakxawopdkpawokcpaxkpxmawpxokap wpa cmawopc mawpwoc maowc'),
(4, 9, '1', 'Good', 2, 'JAjajajaja come to brasil jajajajajaj woooooooo jajaja jajaj jaja kaka kakak ka ka kadk akdwd awid jiajiwc'),
(5, 9, '1', 'Good', 3, 'JAjajajaja come to brasil jajajajajaj woooooooo jajaja jajaj jaja kaka kakadadiajiwc'),
(6, 9, '1', 'Good', 1, 'Horrible product never buying again wow everything is fake this isnt even a real store'),
(7, 9, '1', 'Good', 2, 'JAjajajaja come to brasil jajajajajaj woooooooo jajaja jajaj jaja kaka kakak ka ka kadk akdwd awid jiajiwc'),
(8, 9, 'a', 'Very Good', 5, 'dadawdadawdwad'),
(9, 9, 'a', 'Very Bad', 1, 'Test'),
(10, 9, 'a', 'Very Good', 5, 'Amazing Product , '),
(11, 9, 'a', 'Very Good', 5, 'Very good product '),
(12, 9, 'a', 'Very Bad', 1, 'So sad and shocked :('),
(13, 4, 'a', 'Very Good', 5, 'Nice'),
(14, 10, 'a', 'Very Good', 5, 'i really really like it , its perfect'),
(22, 9, 'a', 'Average', 3, 'test'),
(23, 10, 'a', 'Average', 3, 'aaaaa');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_info`
--
ALTER TABLE `admin_info`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `bannedusers`
--
ALTER TABLE `bannedusers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_productid` (`product`),
  ADD KEY `customeremail` (`email`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer_info`
--
ALTER TABLE `customer_info`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `customer_orders`
--
ALTER TABLE `customer_orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_customeremail` (`customer_email`);

--
-- Indexes for table `deleted_products`
--
ALTER TABLE `deleted_products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_fk` (`category`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_customeremailll` (`email`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_customeremaill` (`email`),
  ADD KEY `fkpro` (`productid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bannedusers`
--
ALTER TABLE `bannedusers`
  MODIFY `id` int(30) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `customer_orders`
--
ALTER TABLE `customer_orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `deleted_products`
--
ALTER TABLE `deleted_products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `customeremail` FOREIGN KEY (`email`) REFERENCES `customer_info` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_productid` FOREIGN KEY (`product`) REFERENCES `product` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `customer_orders`
--
ALTER TABLE `customer_orders`
  ADD CONSTRAINT `fk_customeremail` FOREIGN KEY (`customer_email`) REFERENCES `customer_info` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `category_fk` FOREIGN KEY (`category`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `fk_customeremailll` FOREIGN KEY (`email`) REFERENCES `customer_info` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `fk_customeremaill` FOREIGN KEY (`email`) REFERENCES `customer_info` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkpro` FOREIGN KEY (`productid`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
