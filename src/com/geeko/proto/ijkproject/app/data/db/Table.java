package com.geeko.proto.ijkproject.app.data.db;

import android.provider.BaseColumns;

/**
 * Created by Seonyong on 2014-03-28.
 */
public final class Table {
	public Table() {
	}

	public static abstract class UserTableEntry implements BaseColumns {
		public static final String TABLE_NAME = "user";
		public static final String COLUMN_NAME_NICKNAME = "nickname";
		public static final String COLUMN_NAME_PHONE = "phone";
		public static final String COLUMN_NAME_REGION = "region";
		public static final String COLUMN_NAME_PERIOD = "period";
	}

	public static abstract class UsersTableEntry implements BaseColumns {
		public static final String TABLE_NAME = "users";
		public static final String COLUMN_NAME_ENTRY_ID = "id";
		public static final String COLUMN_NAME_FOREIGN_KEY = "foreignkey";
		public static final String COLUMN_NAME_PHONE = "phonenum";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_REGION = "region";
		public static final String COLUMN_NAME_WORKINGPERIOD = "workingperiod";
		public static final String COLUMN_NAME_PUSHSERVICE = "pushservice";
		public static final String COLUMN_NAME_STATUS = "status";
	}

	public static abstract class OwnMachinesTableEntry implements BaseColumns {
		public static final String TABLE_NAME = "ownmachines";
		public static final String COLUMN_NAME_ENTRY_ID = "phonenum";
		public static final String COLUMN_NAME_ID_MACHINE = "idmachine";
		public static final String COLUMN_NAME_NUMMACHINE = "nummachine";
	}

	public static abstract class MachinesTableEntry implements BaseColumns {
		public static final String TABLE_NAME = "machines";
		public static final String COLUMN_NAME_ENTRY_ID = "id";
		public static final String COLUMN_NAME_CODE = "code";
		public static final String COLUMN_NAME_MODELNAME = "modelname";
		public static final String COLUMN_NAME_TYPE = "type";
		public static final String COLUMN_NAME_SIZE = "size";
	}

	public static abstract class UsersIdTableEntry implements BaseColumns {
		public static final String TABLE_NAME = "ids";
		public static final String COLUMN_NAME_VALUE = "value";
	}

}