/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.model.bo;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年07月17日 下午2:31 $
 */
public class ClusterHealthBO {
    private String cluster_name;
    private String status;
    private boolean timed_out;
    private Integer number_of_nodes;
    private Integer number_of_data_nodes;
    private Integer active_primary_shards;
    private Integer active_shards;
    private Integer relocating_shards;
    private Integer initializing_shards;
    private Integer unassigned_shards;
    private Integer delayed_unassigned_shards;
    private Integer number_of_pending_tasks;
    private Integer number_of_in_flight_fetch;
    private Integer task_max_waiting_in_queue_millis;
    private Integer active_shards_percent_as_number;

    public String getCluster_name() {
        return cluster_name;
    }

    public void setCluster_name(String cluster_name) {
        this.cluster_name = cluster_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isTimed_out() {
        return timed_out;
    }

    public void setTimed_out(boolean timed_out) {
        this.timed_out = timed_out;
    }

    public Integer getNumber_of_nodes() {
        return number_of_nodes;
    }

    public void setNumber_of_nodes(Integer number_of_nodes) {
        this.number_of_nodes = number_of_nodes;
    }

    public Integer getNumber_of_data_nodes() {
        return number_of_data_nodes;
    }

    public void setNumber_of_data_nodes(Integer number_of_data_nodes) {
        this.number_of_data_nodes = number_of_data_nodes;
    }

    public Integer getActive_primary_shards() {
        return active_primary_shards;
    }

    public void setActive_primary_shards(Integer active_primary_shards) {
        this.active_primary_shards = active_primary_shards;
    }

    public Integer getActive_shards() {
        return active_shards;
    }

    public void setActive_shards(Integer active_shards) {
        this.active_shards = active_shards;
    }

    public Integer getRelocating_shards() {
        return relocating_shards;
    }

    public void setRelocating_shards(Integer relocating_shards) {
        this.relocating_shards = relocating_shards;
    }

    public Integer getInitializing_shards() {
        return initializing_shards;
    }

    public void setInitializing_shards(Integer initializing_shards) {
        this.initializing_shards = initializing_shards;
    }

    public Integer getUnassigned_shards() {
        return unassigned_shards;
    }

    public void setUnassigned_shards(Integer unassigned_shards) {
        this.unassigned_shards = unassigned_shards;
    }

    public Integer getDelayed_unassigned_shards() {
        return delayed_unassigned_shards;
    }

    public void setDelayed_unassigned_shards(Integer delayed_unassigned_shards) {
        this.delayed_unassigned_shards = delayed_unassigned_shards;
    }

    public Integer getNumber_of_pending_tasks() {
        return number_of_pending_tasks;
    }

    public void setNumber_of_pending_tasks(Integer number_of_pending_tasks) {
        this.number_of_pending_tasks = number_of_pending_tasks;
    }

    public Integer getNumber_of_in_flight_fetch() {
        return number_of_in_flight_fetch;
    }

    public void setNumber_of_in_flight_fetch(Integer number_of_in_flight_fetch) {
        this.number_of_in_flight_fetch = number_of_in_flight_fetch;
    }

    public Integer getTask_max_waiting_in_queue_millis() {
        return task_max_waiting_in_queue_millis;
    }

    public void setTask_max_waiting_in_queue_millis(Integer task_max_waiting_in_queue_millis) {
        this.task_max_waiting_in_queue_millis = task_max_waiting_in_queue_millis;
    }

    public Integer getActive_shards_percent_as_number() {
        return active_shards_percent_as_number;
    }

    public void setActive_shards_percent_as_number(Integer active_shards_percent_as_number) {
        this.active_shards_percent_as_number = active_shards_percent_as_number;
    }
}
