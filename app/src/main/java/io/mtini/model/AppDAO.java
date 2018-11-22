package io.mtini.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AppDAO implements AppDAOInterface{

    public static String DATABASE_NAME = "EstateDatabase";
    public static int DATABASE_VERSION = 1;
    public static String TAG = "EstateAdaptor";
    public static String ESTATE_TABLE = "Estate";
    public static String TENANT_TABLE = "Tenant";

    Context context;
    DBHelper mDbHelper;
    SQLiteDatabase database;
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public AppDAO(Context context) {
        this.context = context;
    }

    public AppDAO open() throws SQLException {
        mDbHelper = new DBHelper(context);
        database = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        if (mDbHelper != null){
            mDbHelper.close();
        }
    }


    @Override
    public EstateModel getEstateById(String id) {
        database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+ESTATE_TABLE+" where "+EstateTblDescription.KEY_ID.label+" = ? ", new String[]{id});
        int count = cursor.getCount();
        if(count >= 1){
            cursor.moveToNext();
            return new EstateModel(
                    //String id, String name, String address, TYPE type
                    UUID.fromString(cursor.getString(cursor.getColumnIndex(EstateTblDescription.KEY_ID.label))),
                    cursor.getString(cursor.getColumnIndex(EstateTblDescription.KEY_NAME.label)),
                    cursor.getString(cursor.getColumnIndex(EstateTblDescription.ADDRESS.label)),
                    EstateModel.TYPE.getType(cursor.getString(cursor.getColumnIndex(EstateTblDescription.TYPE.label)))
            );
        }
        return null;
    }


    @Override
    public TenantModel getTenantById(String id) {
        database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TENANT_TABLE+" where "+TenantTblDescription.KEY_ID.label+" = ? ", new String[]{id});
        int count = cursor.getCount();
        if(count >= 1){
            cursor.moveToNext();
            return new TenantModel(
                    UUID.fromString(cursor.getString(cursor.getColumnIndex(TenantTblDescription.KEY_ID.label))),
                    UUID.fromString(cursor.getString(cursor.getColumnIndex(TenantTblDescription.KEY_ESTATE_ID.label))),
                    cursor.getString(cursor.getColumnIndex(TenantTblDescription.KEY_NAME.label)),
                    cursor.getString(cursor.getColumnIndex(TenantTblDescription.BLDG_NUMBER.label)),
                    cursor.getString(cursor.getColumnIndex(TenantTblDescription.DESCRIPTION.label)),
                    new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex(TenantTblDescription.RENT_DUE.label)))),
                    TenantModel.STATUS.getStatus(cursor.getString(cursor.getColumnIndex(TenantTblDescription.STATUS.label))),
                    BigDecimal.valueOf(cursor.getInt(cursor.getColumnIndex(TenantTblDescription.RENT.label))),
                    BigDecimal.valueOf(cursor.getInt(cursor.getColumnIndex(TenantTblDescription.BALANCE.label))),
                    cursor.getString(cursor.getColumnIndex(TenantTblDescription.CONTACTS.label))
            );
        }
        return null;
    }

    @Override
    public List<EstateModel> getMyEstateList() {
        database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+ESTATE_TABLE, null);
        int count = cursor.getCount();
        List<EstateModel> ret = new ArrayList<EstateModel>(count);
        if(count >= 1){
            while(cursor.moveToNext()) {
                ret.add (new EstateModel(
                        //String id, String name, String address, TYPE type
                        UUID.fromString(cursor.getString(cursor.getColumnIndex(EstateTblDescription.KEY_ID.label))),
                        cursor.getString(cursor.getColumnIndex(EstateTblDescription.KEY_NAME.label)),
                        cursor.getString(cursor.getColumnIndex(EstateTblDescription.ADDRESS.label)),
                        EstateModel.TYPE.getType(cursor.getString(cursor.getColumnIndex(EstateTblDescription.TYPE.label)))
                ));
            };
        };
        cursor.close();
        database.close();
        //mDbHelper.close();
        return ret;
    }

    @Override
    public List<TenantModel> getTenantList(EstateModel propertyInfo) {

        database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TENANT_TABLE+" where "+TenantTblDescription.KEY_ESTATE_ID.label+" = ?", new String[]{propertyInfo.id+""});
        int count = cursor.getCount();
        List<TenantModel> ret = new ArrayList<TenantModel>(count);
        if(count >= 1){
            while(cursor.moveToNext()) {
                try {
                    ret.add(new TenantModel(
                            //int id, int estateId, String name, String buildingNumber, String description, Date rentDueDate, STATUS status, BigDecimal rent, BigDecimal balance, String contacts
                            UUID.fromString(cursor.getString(cursor.getColumnIndex(TenantTblDescription.KEY_ID.label))),
                            UUID.fromString(cursor.getString(cursor.getColumnIndex(TenantTblDescription.KEY_ESTATE_ID.label))),
                            cursor.getString(cursor.getColumnIndex(TenantTblDescription.KEY_NAME.label)),
                            cursor.getString(cursor.getColumnIndex(TenantTblDescription.BLDG_NUMBER.label)),
                            cursor.getString(cursor.getColumnIndex(TenantTblDescription.DESCRIPTION.label)),
                            new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex(TenantTblDescription.RENT_DUE.label)))),
                            TenantModel.STATUS.getStatus(cursor.getString(cursor.getColumnIndex(TenantTblDescription.STATUS.label))),
                            BigDecimal.valueOf(cursor.getInt(cursor.getColumnIndex(TenantTblDescription.RENT.label))),
                            BigDecimal.valueOf(cursor.getInt(cursor.getColumnIndex(TenantTblDescription.BALANCE.label))),
                            cursor.getString(cursor.getColumnIndex(TenantTblDescription.CONTACTS.label))
                    ));
                }catch(Exception e){throw new RuntimeException(e.getMessage(),e);}
            };
            };
        cursor.close();
        database.close();
        //mDbHelper.close();
        return ret;
    }

    @Override
    public EstateModel addEstate(EstateModel newProperty) {

        database = mDbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put(EstateTblDescription.KEY_ID.label, newProperty.id.toString());
        content.put(EstateTblDescription.KEY_NAME.label, newProperty.name);
        content.put(EstateTblDescription.ADDRESS.label, newProperty.address);
        content.put(EstateTblDescription.CONTACTS.label, newProperty.contacts);
        content.put(EstateTblDescription.TYPE.label, newProperty.type.name());
        content.put(EstateTblDescription.DESCRIPTION.label, newProperty.getDescription());

        long rowId = database.insert(ESTATE_TABLE, null, content);
        database.close();

        if(rowId==-1)return null;//failed

        return newProperty;
    }

    @Override
    public TenantModel addTenant(TenantModel newtenant, EstateModel estate) {

        database = mDbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();

        content.put(TenantTblDescription.KEY_ID.label, newtenant.id.toString());
        content.put(TenantTblDescription.KEY_NAME.label, newtenant.name);

        if(estate!=null)
            content.put(TenantTblDescription.KEY_ESTATE_ID.label, estate.id.toString());
        else
            content.put(TenantTblDescription.KEY_ESTATE_ID.label, newtenant.estateId.toString());

        content.put(TenantTblDescription.CONTACTS.label, newtenant.contacts);
        content.put(TenantTblDescription.BALANCE.label, newtenant.balance==null?0:newtenant.balance.intValue());
        content.put(TenantTblDescription.RENT.label, newtenant.rent==null?0:newtenant.rent.intValue());

        content.put(TenantTblDescription.RENT_DUE.label, newtenant.rentDueDate.getTime());
        content.put(TenantTblDescription.STATUS.label, newtenant.status.name());

        content.put(TenantTblDescription.BLDG_NUMBER.label, newtenant.buildingNumber);
        content.put(TenantTblDescription.DESCRIPTION.label, newtenant.getDescription());

        long rowId = database.insert(TENANT_TABLE, null, content);
        database.close();

        if(rowId==-1)return null;//failed

        //newtenant.setId(rowId);

        return newtenant;
    }

    @Override
    public boolean deleteEstate(EstateModel property) {
        int doneDelete = 0;
        database = mDbHelper.getWritableDatabase();
        doneDelete = database.delete(ESTATE_TABLE, " "+EstateTblDescription.KEY_ID.label+" = ? ", new String[]{property.id.toString()});
        Log.w(TAG, Integer.toString(doneDelete));
        database.close();
        return doneDelete > 0;
    }

    @Override
    public boolean deleteTenant(TenantModel tenant, EstateModel property) {
        int doneDelete = 0;
        database = mDbHelper.getWritableDatabase();
        doneDelete = database.delete(TENANT_TABLE, " "+EstateTblDescription.KEY_ID.label+" = ? AND "+TenantTblDescription.KEY_ID.label+" = ? ", new String[]{property.id.toString(), tenant.id.toString()});
        Log.w(TAG, Integer.toString(doneDelete));
        database.close();
        return doneDelete > 0;
    }

    @Override
    public EstateModel updateEstate(EstateModel property) {
        database = mDbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();

        //content.put(EstateTblDescription.KEY_ID.label, property.id.toString());
        content.put(EstateTblDescription.KEY_NAME.label, property.name);
        content.put(EstateTblDescription.ADDRESS.label, property.address);
        content.put(EstateTblDescription.CONTACTS.label, property.contacts);
        content.put(EstateTblDescription.TYPE.label, property.type.name());
        content.put(EstateTblDescription.DESCRIPTION.label, property.getDescription());

        int rowsaffected = database.update(ESTATE_TABLE, content, " "+EstateTblDescription.KEY_ID.label+" = ? ", new String[]{property.id.toString()});

        database.close();

        if(rowsaffected!=1)return null;

        return property;
    }

    @Override
    public TenantModel updateTenant(TenantModel newtenant) {
        return updateTenant(newtenant,null);
    }

    @Override
    public TenantModel updateTenant(TenantModel newtenant, EstateModel estate) {

        database = mDbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();

        content.put(TenantTblDescription.KEY_ID.label, newtenant.id.toString());
        content.put(TenantTblDescription.KEY_NAME.label, newtenant.name);

        if(estate!=null)
            content.put(TenantTblDescription.KEY_ESTATE_ID.label, estate.id.toString());
        else
            content.put(TenantTblDescription.KEY_ESTATE_ID.label, newtenant.estateId.toString());

        content.put(TenantTblDescription.CONTACTS.label, newtenant.contacts);
        content.put(TenantTblDescription.BALANCE.label, newtenant.balance==null?0:newtenant.balance.intValue());
        content.put(TenantTblDescription.RENT.label, newtenant.rent==null?0:newtenant.rent.intValue());

        content.put(TenantTblDescription.RENT_DUE.label, newtenant.rentDueDate==null?null:newtenant.rentDueDate.getTime());
        content.put(TenantTblDescription.STATUS.label, newtenant.status.name());

        content.put(TenantTblDescription.BLDG_NUMBER.label, newtenant.buildingNumber);
        content.put(TenantTblDescription.DESCRIPTION.label, newtenant.getDescription());

        long rowId = database.update(TENANT_TABLE,  content, " "+TenantTblDescription.KEY_ID.label+" = ? ", new String[]{newtenant.id.toString()} );
        database.close();

        if(rowId==-1)return null;//failed

        return newtenant;
    }

    private static final String ESTATE_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + ESTATE_TABLE + " (" +
            EstateTblDescription.KEY_ID.label + " "+EstateTblDescription.KEY_ID.type+" PRIMARY KEY," +
            EstateTblDescription.KEY_NAME.label + " "+EstateTblDescription.KEY_NAME.type+","+
            EstateTblDescription.CONTACTS.label + " "+EstateTblDescription.CONTACTS.type+","+
            EstateTblDescription.ADDRESS.label + " "+EstateTblDescription.ADDRESS.type+"," +
            EstateTblDescription.DESCRIPTION.label + " "+EstateTblDescription.DESCRIPTION.type+"," +
            EstateTblDescription.TYPE.label + " "+EstateTblDescription.TYPE.type+");" ;

    private static final String TENANT_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TENANT_TABLE + " (" +
            TenantTblDescription.KEY_ID.label + " "+TenantTblDescription.KEY_ID.type+" PRIMARY KEY," +
            TenantTblDescription.KEY_ESTATE_ID.label + " "+TenantTblDescription.KEY_ESTATE_ID.type+","+
            TenantTblDescription.KEY_NAME.label + " "+TenantTblDescription.KEY_NAME.type+","+
            TenantTblDescription.CONTACTS.label + " "+TenantTblDescription.CONTACTS.type+"," +
            TenantTblDescription.DESCRIPTION.label + " "+TenantTblDescription.DESCRIPTION.type+"," +
            TenantTblDescription.RENT.label + " "+TenantTblDescription.RENT.type+"," +
            TenantTblDescription.RENT_DUE.label + " "+TenantTblDescription.RENT_DUE.type+"," +
            TenantTblDescription.BALANCE.label + " "+TenantTblDescription.BALANCE.type+"," +
            TenantTblDescription.STATUS.label + " "+TenantTblDescription.STATUS.type+"," +
            TenantTblDescription.BLDG_NUMBER.label + " "+TenantTblDescription.BLDG_NUMBER.type+");" ;


    private static class DBHelper extends SQLiteOpenHelper {

        DBHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            Log.w(TAG, ESTATE_TABLE_CREATE);
            db.execSQL(ESTATE_TABLE_CREATE);

            Log.w(TAG, TENANT_TABLE_CREATE);
            db.execSQL(TENANT_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+TENANT_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+ESTATE_TABLE);
            onCreate(db);
        }

    }


    public void dropTables(){

       mDbHelper.onUpgrade(database,0,1);
    }

    //TEST DATA
    public void uploadData(){


        addEstate(new EstateModel( UUID.randomUUID(),"KN Apartment","Kiambu Rd",EstateModel.TYPE.apartment));

        EstateModel offices = new EstateModel( UUID.randomUUID(),"KN Plaza","Kiambu Rd",EstateModel.TYPE.commercial);
        addEstate(offices);

        try {
            addTenant(new TenantModel(UUID.randomUUID(),"Mtu Moja", "N1", "Lounge hall", dateFormat.parse("2018/11/01"), TenantModel.STATUS.paid, BigDecimal.valueOf(10000.00), BigDecimal.valueOf(0.00), "0708555121"), offices);
            addTenant(new TenantModel(UUID.randomUUID(),"Mtu Mwingine", "N3", "Office 1", dateFormat.parse("2018/11/01"), TenantModel.STATUS.paid, BigDecimal.valueOf(3000), BigDecimal.valueOf(0), "0708555222"), offices);
            addTenant(new TenantModel(UUID.randomUUID(),"Watu Wengi", "N4", "Office 2", dateFormat.parse("2018/11/01"), TenantModel.STATUS.paid, BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), "0708555444"), offices);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
