package minimed.data;

public class FieldNames {

	public static class S3BucketKeys {
		public static final String userDataFormat = "-userData.txt";
		public static final String contactDataFormat = "-contactData.txt";
		public static final String allergyDataFormat = "-allergyData.txt";
		public static final String historyDataFormat = "-historyData.txt";
		public static final String medicationDataFormat = "-medicationData.txt";
		//public static final String imageData = "-imageData.txt";
	}
	
	public static class USER {
		public static final String FIRST_NAME = "firstName";
		public static final String MIDDLE_INITIAL = "middleInitial";
		public static final String LAST_NAME = "lastName";
		public static final String BLOOD_TYPE = "bloodType";
		public static final String DOB = "birthDate";
		public static final String GENDER = "gender";
	}

	public static class CONTACT {
		public static final String TYPE = "type";
		public static final String NAME = "name";
		public static final String RELATION = "relation";
		public static final String HOME_PHONE = "homePhone";
		public static final String CELL_PHONE = "cellPhone";
		public static final String WORK_PHONE = "workPhone";
	}
	
	public static class MEDICAL_HISTORY {
		public static final String EVENT_TITLE = "eventTitle";
		public static final String DESCRIPTION = "description";
		public static final String DATE = "date";
	}
	
	public static class ALLERGY {
		public static final String NAME = "name";
		public static final String TYPE = "type";
		public static final String DETAILS = "details";
	}	
	
	public static class MEDICATION {
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
		public static final String SOURCE = "source";
		public static final String AMOUNT = "amount";
		public static final String INTERVAL = "interval";
	}
	
}
