CREATE TABLE IF NOT EXISTS `seasons`
(
    `id`            VARCHAR(36) NOT NULL,
    `season_number` INT,
    `tv_shows_id`   VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`, `tv_shows_id`),
    INDEX `fk_table1_tv_shows_idx` (`tv_shows_id` ASC),
    CONSTRAINT `fk_table1_tv_shows`
        FOREIGN KEY (`tv_shows_id`)
            REFERENCES `tv_shows` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARSET = UTF8;