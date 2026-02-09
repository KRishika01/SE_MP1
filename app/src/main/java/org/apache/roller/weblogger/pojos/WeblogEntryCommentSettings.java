package org.apache.roller.weblogger.pojos;

import java.io.Serializable;

/**
 * Encapsulates comment settings for a WeblogEntry.
 */
public class WeblogEntryCommentSettings implements Serializable {
    
    private Boolean   allowComments = Boolean.TRUE;
    private Integer   commentDays   = 7;
    
    public WeblogEntryCommentSettings() {}

    public Boolean getAllowComments() {
        return allowComments;
    }

    public void setAllowComments(Boolean allowComments) {
        this.allowComments = allowComments;
    }

    public Integer getCommentDays() {
        return commentDays;
    }

    public void setCommentDays(Integer commentDays) {
        this.commentDays = commentDays;
    }
}
