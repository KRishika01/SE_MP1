package org.apache.roller.weblogger.pojos;

import java.io.Serializable;
import java.sql.Timestamp;
import org.apache.roller.weblogger.pojos.WeblogEntry.PubStatus;

/**
 * Encapsulates publication settings for a WeblogEntry.
 */
public class WeblogEntryPublicationSettings implements Serializable {
    
    private Timestamp pubTime       = null;
    private Timestamp updateTime    = null;
    private PubStatus status        = PubStatus.DRAFT;
    
    public WeblogEntryPublicationSettings() {}

    public Timestamp getPubTime() {
        return pubTime;
    }

    public void setPubTime(Timestamp pubTime) {
        this.pubTime = pubTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public PubStatus getStatus() {
        return status;
    }

    public void setStatus(PubStatus status) {
        this.status = status;
    }
}
