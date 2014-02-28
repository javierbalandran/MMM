package minimed.data;

import java.util.Date;

public class Medication {
	private String name;
	private String description;
	private String source;
	private int amount;
	private int interval;
	private Date startDate;
	private Date endDate;
	private boolean currentlyTaking;
	
	public Medication(String name, String description, String source,
			int amount, int interval, Date startDate, Date endDate,
			boolean currentlyTaking) {
		this.name = name;
		this.description = description;
		this.source = source;
		this.amount = amount;
		this.interval = interval;
		this.startDate = startDate;
		this.endDate = endDate;
		this.currentlyTaking = currentlyTaking;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isCurrentlyTaking() {
		return currentlyTaking;
	}

	public void setCurrentlyTaking(boolean currentlyTaking) {
		this.currentlyTaking = currentlyTaking;
	}
	
}
