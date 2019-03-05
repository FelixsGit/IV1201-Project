-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Värd: 127.0.0.1
-- Tid vid skapande: 31 jan 2019 kl 16:04
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
DROP TABLE IF EXISTS `availability`;
DROP TABLE IF EXISTS `competence`;
DROP TABLE IF EXISTS `competence_profile`;
DROP TABLE IF EXISTS `person`;
DROP TABLE IF EXISTS `role`;
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
  `name` varchar(255) DEFAULT NULL,
  `nameEn` varchar(255) DEFAULT NULL
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

--
-- AUTO_INCREMENT för dumpade tabeller
--

--
-- AUTO_INCREMENT för tabell `availability`
--
ALTER TABLE `availability`
  MODIFY `availability_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT för tabell `competence`
--
ALTER TABLE `competence`
  MODIFY `competence_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT för tabell `competence_profile`
--
ALTER TABLE `competence_profile`
  MODIFY `competence_profile_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT för tabell `person`
--
ALTER TABLE `person`
  MODIFY `person_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT för tabell `role`
--
ALTER TABLE `role`
  MODIFY `role_id` bigint(20) NOT NULL AUTO_INCREMENT;

INSERT INTO role (role_id, name) VALUES (1, 'recruit');
INSERT INTO role (role_id, name) VALUES (2, 'applicant');
INSERT INTO person (person_id, name, surname, username, password, role_id)
VALUES (1, 'Greta', 'Borg', 'borg', 'wl9nk23a', 1);
INSERT INTO person (person_id, name, surname, ssn, email, role_id)
VALUES (2, 'Per', 'Strand', '19671212-1211', 'per@strand.kth.se', 2);
INSERT INTO availability (availability_id, person_id, from_date, to_date)
VALUES (1, 2, '2014-02-23', '2014-05-25');
INSERT INTO availability (availability_id, person_id, from_date, to_date)
VALUES (2, 2, '2014-07-10', '2014-08-10');
INSERT INTO competence (competence_id, name, nameEn)
VALUES (1, 'Korvgrillning', 'HotDogChef');
INSERT INTO competence (competence_id, name, nameEn)
VALUES (2, 'Karuselldrift', 'CarouselOperator');
INSERT INTO competence_profile (competence_profile_id, person_id,
competence_id, years_of_experience)
VALUES (1, 2, 1, 3.5);
INSERT INTO competence_profile (competence_profile_id, person_id,
competence_id, years_of_experience)
VALUES (2, 2, 2, 2.0);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
