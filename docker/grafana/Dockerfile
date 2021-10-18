FROM grafana/grafana

COPY influx-datasource.yaml /etc/grafana/provisioning/datasources/
COPY loki-datasource.yaml /etc/grafana/provisioning/datasources/
COPY dashboard-provider.yaml /etc/grafana/provisioning/dashboards/

COPY dashboards/* /var/lib/grafana/dashboards/
