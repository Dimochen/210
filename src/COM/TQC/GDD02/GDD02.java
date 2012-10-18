package COM.TQC.GDD02;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
//import android.widget.Toast;

public class GDD02 extends Activity {
	private static final String DBNAME = "MY_DB";
	private static final String TABLENAME = "MY_TABLE";
	private static final String FIELD01_NAME = "_id";
	private static final String FIELD02_NAME = "_eat";
	private static final String FIELD03_NAME = "_timey";
	private static final String FIELD04_NAME = "_timem";
	private static final String FIELD05_NAME = "_timed";
	private static final String FIELD06_NAME = "_timeh";
	private static final String FIELD07_NAME = "_timemi";
	private static final String FIELD08_NAME = "_Bsugar";
	private static final String FIELD09_NAME = "_down";
	private static final String FIELD10_NAME = "_up";
	private static final String FIELD11_NAME = "_Fsugar";
	private static final String FIELD12_NAME = "_TestTime";
	private SQLiteDatabase dataBase;
	private android.database.Cursor cursor;
	private int _id = -1;
	private int testtime = 0;
	private EditText eat;
	private EditText Bsugar;
	private EditText down;
	private EditText up;
	private EditText Fsugar;
	private Button Button01;
	private Button Button02;
	private Button Button03;
	private ListView ListView01;
	private Button button1;
	private TextView textView1;
	private Spinner whicheat;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        whicheat = (Spinner) findViewById(R.id.spinner);
        eat = (EditText) findViewById(R.id.EditText01);
        Bsugar = (EditText) findViewById(R.id.editText1);
        down = (EditText) findViewById(R.id.editText2);
        up = (EditText) findViewById(R.id.editText3);
        Fsugar = (EditText) findViewById(R.id.editText4);
        Button01 = (Button) findViewById(R.id.BtnAdd);
        Button02 = (Button) findViewById(R.id.BtnUpdate);
        Button03 = (Button) findViewById(R.id.BtnDelete);     
        ListView01 = (ListView) findViewById(R.id.ListView01);
        button1 = (Button) findViewById(R.id.button1);
        textView1 = (TextView) findViewById(R.id.textView1);
        
        final String[] s = new String[] {  
          	  "早餐前", "午餐前", "晚餐前", "睡覺前"  
          	 };        

  	    ArrayAdapter<String> aadapter = new ArrayAdapter<String>(this,  
  	    android.R.layout.simple_spinner_item, s);  
  	    aadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
  	    whicheat.setAdapter(aadapter);  
  	   
  	    whicheat.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				testtime = arg2 + 1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
        
        Time t = new Time();
        t.setToNow(); // 取得系统时间。
        final int year = t.year;
        final int month = t.month;
        final int date = t.monthDay;
        final int hour = t.hour;    // 0-23
        final int minute = t.minute;
        
        Button02.setEnabled(false);
        Button03.setEnabled(false);
        
        String CREATE_SQL = "create table if not exists "+TABLENAME+" ("+FIELD01_NAME+" integer primary key autoincrement, "+FIELD02_NAME+" varchar not null, "+FIELD03_NAME+" varchar not null, "+FIELD04_NAME+" varchar not null, "+FIELD05_NAME+" varchar not null, "+FIELD06_NAME+" varchar not null, "+FIELD07_NAME+" varchar not null, "+FIELD08_NAME+" varchar not null, "+FIELD09_NAME+" varchar not null, "+FIELD10_NAME+" varchar not null, "+FIELD11_NAME+" varchar not null, "+FIELD12_NAME+" varchar not null);";
    	dataBase = openOrCreateDatabase(DBNAME,MODE_WORLD_WRITEABLE, null); 	
    	dataBase.execSQL(CREATE_SQL);
        cursor = dataBase.query(TABLENAME, null, null, null, null, null, null);
        android.widget.SimpleCursorAdapter adapter = new android.widget.SimpleCursorAdapter(this, R.layout.list,
        		cursor, new String[]
                { FIELD02_NAME }, new int[]
                { R.id.CheckedTextView01 });
        ListView01.setAdapter(adapter);
        
        Button01.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	if(!eat.getText().equals("")){
			//		insert(""+eat.getText(),""+year,""+(month+1),""+date,""+hour,""+minute,""+Bsugar.getText(),""+down.getText(),""+up.getText(),""+Fsugar.getText(),""+testtime);
			//	}
			}});
        Button02.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(_id!=-1 && !eat.getText().equals("")){
					update(_id,""+eat.getText());
					Button02.setEnabled(false);
					Button03.setEnabled(false);
				}
				
			}});
        Button03.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(_id!=-1){
					delete(_id);
					Button02.setEnabled(false);
					Button03.setEnabled(false);
				}
				
			}});
       
        button1.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!(eat.getText().equals("") || Bsugar.getText().equals("") || Fsugar.getText().equals("") || up.getText().equals("") || down.getText().equals(""))){
					insert(""+eat.getText(),""+year,""+(month+1),""+date,""+hour,""+minute,""+Bsugar.getText(),""+down.getText(),""+up.getText(),""+Fsugar.getText(),""+testtime);
				}
				
				int eatTime = Integer.parseInt(eat.getText().toString());
				Double bsugar = Double.parseDouble(Bsugar.getText().toString());
				Double Down = Double.parseDouble(down.getText().toString());
				Double Up = Double.parseDouble(up.getText().toString());
				Double fsugar = Double.parseDouble(Fsugar.getText().toString());
				Double total = ((Up*eatTime-(fsugar-bsugar))/Down);
				int rows_num = cursor.getCount();	//取得資料表列數
				int number = 0; 
				int a = 0;
				int b = 80;
				if(testtime == 1)
				{	
				if(rows_num != 0) {
					cursor.moveToLast();			//將指標移至最後一筆資料
					for(int i=rows_num; i>0; i--) {
						//int id = cursor.getInt(0);	//取得第0欄的資料，根據欄位type使用適當語法
									
						    if(cursor.getString(11).equals("2") && number<2)
						    {
						    	if(number == 0)
						    	{
						    	 a = Integer.parseInt(cursor.getString(8));
						    	 number++;
						    	}
						    	if(number == 1)
						    	{
						    	 b = Integer.parseInt(cursor.getString(8));
						    	 number++;
						    	}	 
						    }
							
						cursor.moveToPrevious();		//將指標移至上一筆資料
					}
					
				 }
				}
				if(a <= 70 && b <= 70){total = total-2;}
				if(a > 120 && b > 120){total = total+2;}
				if(bsugar < 35){total = total-2;}
				if(bsugar <= 70){total = total-1;}
				if(120 < bsugar && bsugar <=240){total = total+1;}
				if(240 < bsugar){total = total+2;}
			    textView1.setText(""+total);
				
			}});
        
        ListView01.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(android.widget.AdapterView<?> arg0,
					View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				cursor.moveToPosition(arg2);
				_id = cursor.getInt(0);
				eat.setText(cursor.getString(1));
				Button02.setEnabled(true);
		        Button03.setEnabled(true);
			}});
        
    }
    
    public class whicheat extends Activity {  
    	  final String[] s = new String[] {  
    	  "Bahams", "Bahrain", "Barbados", "Belgium", "Beline",  
    	  "France", "Italy", "Germany", "Spain"  
    	 };  
    	   
    	 @Override public void  
    	 onCreate(Bundle icicle) {  
    	  super.onCreate(icicle);  
    	  setContentView(R.layout.main);  
    	  
    	  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  
    	   android.R.layout.simple_spinner_item, s);  
    	  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
    	  //Spinner sp = (Spinner) findViewById(R.id.spinner_1);  
    	  whicheat.setAdapter(adapter);  
    	 }  
    	}
    
    private void insert(String text,String text1,String text2,String text3,String text4,String text5,String text6,String text7,String text8,String text9,String text10){
    	
    	android.content.ContentValues cv = new android.content.ContentValues();
    	cv.put(FIELD02_NAME, text);
        dataBase.insert(TABLENAME, null, cv);
        cv.put(FIELD03_NAME, text1);
        dataBase.insert(TABLENAME, null, cv);
        cv.put(FIELD04_NAME, text2);
        dataBase.insert(TABLENAME, null, cv);
        cv.put(FIELD05_NAME, text3);
        dataBase.insert(TABLENAME, null, cv);
        cv.put(FIELD06_NAME, text4);
        dataBase.insert(TABLENAME, null, cv);
        cv.put(FIELD07_NAME, text5);
        dataBase.insert(TABLENAME, null, cv);
        cv.put(FIELD08_NAME, text6);
        dataBase.insert(TABLENAME, null, cv);
        cv.put(FIELD09_NAME, text7);
        dataBase.insert(TABLENAME, null, cv);
        cv.put(FIELD10_NAME, text8);
        dataBase.insert(TABLENAME, null, cv);
        cv.put(FIELD11_NAME, text9);
        dataBase.insert(TABLENAME, null, cv);
        cv.put(FIELD12_NAME, text10);
        dataBase.insert(TABLENAME, null, cv);
       
        cursor.requery();
    }
    private void update(int id,String text){
    	String where = FIELD01_NAME + "= ?";
    	String[] wherevalue = {Integer.toString(id)};
    	android.content.ContentValues cv = new android.content.ContentValues();
    	cv.put(FIELD02_NAME, text);
        dataBase.update(TABLENAME, cv, where, wherevalue);
        cursor.requery();
        
    }
    private void delete(int id){
    	String where = FIELD01_NAME + "=?";
        String[] wherevalue = {Integer.toString(id)};
        dataBase.delete(TABLENAME, where, wherevalue);
        cursor.requery();
        
    }
    
}