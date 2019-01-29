-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Värd: 127.0.0.1
-- Tid vid skapande: 29 jan 2019 kl 13:07
-- Serverversion: 10.1.36-MariaDB
-- PHP-version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databas: `recruitmentdb`
--

-- --------------------------------------------------------

--
-- Tabellstruktur `availability`
--

CREATE TABLE `availability` (
  `availability_id` bigint(20) NOT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  `from_date` date DEFAULT NULL,
  `to_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellstruktur `competence`
--

CREATE TABLE `competence` (
  `competence_id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellstruktur `competence_profile`
--

CREATE TABLE `competence_profile` (
  `competence_profile_id` bigint(20) NOT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  `competence_id` bigint(20) DEFAULT NULL,
  `years_of_experience` decimal(4,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellstruktur `person`
--

CREATE TABLE `person` (
  `person_id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `ssn` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellstruktur `role`
--

CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Index för dumpade tabeller
--

--
-- Index för tabell `availability`
--
ALTER TABLE `availability`
  ADD PRIMARY KEY (`availability_id`);

--
-- Index för tabell `competence`
--
ALTER TABLE `competence`
  ADD PRIMARY KEY (`competence_id`);

--
-- Index för tabell `competence_profile`
--
ALTER TABLE `competence_profile`
  ADD PRIMARY KEY (`competence_profile_id`);

--
-- Index för tabell `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`person_id`);

--
-- Index för tabell `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
