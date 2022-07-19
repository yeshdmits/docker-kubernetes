FROM liquibase/liquibase
ADD ./changelog /liquibase/changelog
CMD ["sh", "-c", "docker-entrypoint.sh --url=${DB_URL} --username=${DB_USERNAME} --password=${DB_PASSWORD} --classpath=/liquibase/changelog --changeLogFile=changelog-root.xml update"]