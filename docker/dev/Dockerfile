FROM openjdk:11-jdk
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh && \
    useradd -ms /bin/bash pmc && \
    mkdir /opt/protein-mass-calculator && \
    chown pmc:pmc /opt/protein-mass-calculator

USER pmc
WORKDIR /opt/protein-mass-calculator

ENTRYPOINT ["/entrypoint.sh"]