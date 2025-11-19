package com.theraconnect.dto;
 
import java.time.LocalDateTime;
 
public class AvailabilityCalendarDTO {
 
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
 
    public AvailabilityCalendarDTO() {
		super();
	}
 
	public AvailabilityCalendarDTO(Long id, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }
 
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
 
	public LocalDateTime getStart() {
		return start;
	}
 
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
 
	public LocalDateTime getEnd() {
		return end;
	}
 
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
 
  
}
 
 