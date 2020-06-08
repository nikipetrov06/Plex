CREATE TABLE IF NOT EXISTS `episode_issues` (
    `episode_id` VARCHAR(36) NOT NULL,
    `audio` VARCHAR(12),
    `subtitles` VARCHAR(12),
    `imdb_id` VARCHAR(12),
    PRIMARY KEY (`episode_id`),
    INDEX `fk_episode_issues_episodes1_idx` (`episode_id` ASC),
    CONSTRAINT `fk_episode_issues_episodes1`
        FOREIGN KEY (`episode_id`)
            REFERENCES `episodes` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARSET = UTF8;