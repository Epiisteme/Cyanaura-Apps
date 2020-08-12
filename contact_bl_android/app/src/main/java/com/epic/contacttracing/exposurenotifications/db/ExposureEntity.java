package com.epic.contacttracing.exposurenotifications.db;

import androidx.room.*;

import org.threeten.bp.Instant;

@Entity
public class ExposureEntity {
    @PrimaryKey(autoGenerate = true)
    private
    long id = 0;
    @ColumnInfo(name = "date_millis_since_epoch")
    private
    long dateMillisSinceEpoch;
    @ColumnInfo(name = "received_timestamp_ms")
    private
    long receivedTimestampMs = System.currentTimeMillis();


    public String toString() {
            return "ExposureEntity{" +
                    "id=" + getId() +
                    ", dateMillisSinceEpoch=" + getDateMillisSinceEpoch() +
                    "(" + Instant.ofEpochMilli(getDateMillisSinceEpoch()) + ")" +
                    ", receivedTimestampMs=" + getReceivedTimestampMs() +
                    "(" + Instant.ofEpochMilli(getReceivedTimestampMs()) + ")" +
                    '}';
        }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDateMillisSinceEpoch() {
        return dateMillisSinceEpoch;
    }

    public void setDateMillisSinceEpoch(long dateMillisSinceEpoch) {
        this.dateMillisSinceEpoch = dateMillisSinceEpoch;
    }

    public long getReceivedTimestampMs() {
        return receivedTimestampMs;
    }

    public void setReceivedTimestampMs(long receivedTimestampMs) {
        this.receivedTimestampMs = receivedTimestampMs;
    }
}

