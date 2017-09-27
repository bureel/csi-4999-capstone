-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 27, 2017 at 03:46 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cis4999`
--

-- --------------------------------------------------------

--
-- Table structure for table `concernedpublic`
--

CREATE TABLE `concernedpublic` (
  `CPID` int(20) NOT NULL,
  `VID` int(20) NOT NULL,
  `Fname` varchar(20) NOT NULL,
  `Lname` varchar(20) NOT NULL,
  `street` varchar(20) DEFAULT NULL,
  `aptNum` varchar(5) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `zip` varchar(15) DEFAULT NULL,
  `State/Prov` varchar(20) DEFAULT NULL,
  `Country` varchar(20) DEFAULT NULL,
  `Photo` longblob,
  `Phone` varchar(20) DEFAULT NULL,
  `Subscrib` tinyint(1) NOT NULL DEFAULT '0',
  `Email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `fam`
--

CREATE TABLE `fam` (
  `FID` int(20) NOT NULL,
  `E-Mail` varchar(30) NOT NULL,
  `Fname` varchar(20) NOT NULL,
  `Lname` varchar(20) NOT NULL,
  `Street` varchar(20) NOT NULL,
  `aptNum` varchar(5) DEFAULT NULL,
  `City` varchar(20) NOT NULL,
  `State/Prov` text NOT NULL,
  `Country` text NOT NULL,
  `Hphone` varchar(20) DEFAULT NULL,
  `Cphone` varchar(20) DEFAULT NULL,
  `Wphone` varchar(20) DEFAULT NULL,
  `VID` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lawenforecement`
--

CREATE TABLE `lawenforecement` (
  `LID` int(15) NOT NULL,
  `Fname` varchar(20) NOT NULL,
  `Lname` varchar(20) NOT NULL,
  `Precinct` varchar(30) NOT NULL,
  `Email` varchar(20) NOT NULL,
  `badgeNum` varchar(10) NOT NULL,
  `Cphone` varchar(20) DEFAULT NULL,
  `wphone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `E_mail` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `viccontacts`
--

CREATE TABLE `viccontacts` (
  `VCID` int(20) NOT NULL,
  `VID` int(20) NOT NULL,
  `Fname` varchar(20) NOT NULL,
  `Lname` varchar(20) NOT NULL,
  `akaName` varchar(20) DEFAULT NULL,
  `StreetNumber` varchar(10) DEFAULT NULL,
  `Street` int(20) DEFAULT NULL,
  `aptNum` int(5) DEFAULT NULL,
  `zip` int(15) DEFAULT NULL,
  `State/prov` int(20) NOT NULL,
  `Hphone` int(20) DEFAULT NULL,
  `Cphone` int(20) DEFAULT NULL,
  `Wphone` int(20) DEFAULT NULL,
  `facebook` varchar(30) DEFAULT NULL,
  `twitter` varchar(30) DEFAULT NULL,
  `instagram` varchar(30) DEFAULT NULL,
  `Country` varchar(20) NOT NULL,
  `City` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `victim`
--

CREATE TABLE `victim` (
  `VID` int(15) NOT NULL,
  `Fname` varchar(20) NOT NULL,
  `Lname` varchar(20) NOT NULL,
  `FathersName` varchar(40) NOT NULL,
  `MothersName` int(40) NOT NULL,
  `Photo` blob NOT NULL,
  `Race` varchar(20) NOT NULL,
  `hairColor` int(20) NOT NULL,
  `eyeColor` int(20) NOT NULL,
  `DoB` date NOT NULL,
  `Weight` int(3) NOT NULL,
  `Height` int(3) NOT NULL,
  `Cphone` int(12) DEFAULT NULL,
  `facebook` varchar(20) DEFAULT NULL,
  `instagram` int(20) DEFAULT NULL,
  `distMark` varchar(300) DEFAULT NULL,
  `LastLocation` varchar(40) NOT NULL,
  `LastDate` date NOT NULL,
  `Notes` varchar(400) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `concernedpublic`
--
ALTER TABLE `concernedpublic`
  ADD PRIMARY KEY (`CPID`);

--
-- Indexes for table `fam`
--
ALTER TABLE `fam`
  ADD PRIMARY KEY (`FID`),
  ADD UNIQUE KEY `VID` (`VID`);

--
-- Indexes for table `lawenforecement`
--
ALTER TABLE `lawenforecement`
  ADD PRIMARY KEY (`LID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`E_mail`);

--
-- Indexes for table `viccontacts`
--
ALTER TABLE `viccontacts`
  ADD PRIMARY KEY (`VCID`);

--
-- Indexes for table `victim`
--
ALTER TABLE `victim`
  ADD PRIMARY KEY (`VID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `concernedpublic`
--
ALTER TABLE `concernedpublic`
  MODIFY `CPID` int(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `fam`
--
ALTER TABLE `fam`
  MODIFY `FID` int(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `lawenforecement`
--
ALTER TABLE `lawenforecement`
  MODIFY `LID` int(15) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `viccontacts`
--
ALTER TABLE `viccontacts`
  MODIFY `VCID` int(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `victim`
--
ALTER TABLE `victim`
  MODIFY `VID` int(15) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
