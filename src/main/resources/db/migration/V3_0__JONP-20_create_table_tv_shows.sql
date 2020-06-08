CREATE TABLE IF NOT EXISTS `tv_shows`
(
    `id`           VARCHAR(36) NOT NULL,
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
    `audio`        VARCHAR(45),
    `subtitles`        VARCHAR(45),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8;