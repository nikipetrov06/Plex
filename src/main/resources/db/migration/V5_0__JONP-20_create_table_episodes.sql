CREATE TABLE IF NOT EXISTS `episodes`
(
    `id`           VARCHAR(36)  NOT NULL,
    `number`       INT,
    `title`        VARCHAR(45),
    `description`  VARCHAR(255),
    `rating`       DOUBLE,
    `release_date` DATE,
    `director`     VARCHAR(45),
    `writer`       VARCHAR(45),
    `stars`        VARCHAR(45),
    `duration`     INT,
    `imdb_id`      VARCHAR(45),
    `year`         INT,
    `genre`        VARCHAR(45),
    `audio`        VARCHAR(255),
    `subtitles`    VARCHAR(255),
    `seasons_id`   VARCHAR(36),
    PRIMARY KEY (`id`, `seasons_id`),
    INDEX `fk_episodes_seasons1_idx` (`seasons_id` ASC),
    CONSTRAINT `fk_episodes_seasons1`
        FOREIGN KEY (`seasons_id`)
            REFERENCES `seasons` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARSET = UTF8;