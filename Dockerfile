FROM payara/micro
COPY target/customerApp.war $DEPLOY_DIR
