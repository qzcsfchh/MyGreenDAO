# 核心类
## 0. Schema-用于创建实体类及其相应的工具类

        Entity note = schema.addEntity("Note");  //添加一个类名为Note的实体类，对应的表名
        
## 1. DaoMaster-创建BeanSession
* 保存了sqlitedatebase对象以及操作DAO classes（注意：不是对象）;
* 其提供了一些创建和删除table的静态方法，其内部类OpenHelper和DevOpenHelper实现了SQLiteOpenHelper并创建数据库的框架。        
>* create instance        
        
        helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME, null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
               
>* create daoSession

        daoSession = daoMaster.newSession();

## 2. BeanSession-管理&创建BeanDao
        
>* 获得session对象
        
        DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        
>*  核心方法

        BeanDao beanDao=beanSession.getBeanDao();
        注意：beanSession可以放在application中管理；
## 3. BeanDao-用于往对应的表中增删改数据
>* insert
        
        noteDao.insert(note);   //插入数据
>* delete

        noteDao.deleteByKey(noteId);  //删除指定数据
>* update
        
        note.setText("This note has changed.");
        noteDao.update(note);
## 4. Query<Bean>-用于往对应的表中查询数据
>* 获得对象

        Query<Bean> beansQuery = noteDao.queryBuilder()
                            .orderAsc(NoteDao.Properties.Text)   //根据指定字段排序
                            .build();
>* 查询所有结果

        List<Note> notes = notesQuery.list();
## 5. Bean-与表对应的实体类
        
        @Entity
        public class User {
            @Id(autoincrement = true)
            private Long id;
            
            @Index(unique = true)
            private String key;
         
            @Property(nameInDb = "USERNAME")
            private String name;
         
            @NotNull
            private int repos;
         
            @Transient
            private int tempUsageCount;
         
            ...
        }
### 5.1 @Entity 注释修饰实体类（必须）
        
        @Entity(
                // If you have more than one schema, you can tell greenDAO to which schema an entity belongs (pick any string as a name).
                schema = "myschema",
                
                // Flag to make an entity "active": Active entities have update, delete, and refresh methods.
                active = true,
                
                // Specifies the name of the table in the database. By default, the name is based on the entities class name.
                nameInDb = "AWESOME_USERS",
                
                // Define indexes spanning multiple columns here.
                indexes = {
                        @Index(value = "name DESC", unique = true)
                },
                
                // Flag if the DAO should create the database table (default is true). or the table creation is done outside of greenDAO.
                createInDb = false,
         
                // Whether an all properties constructor should be generated. A no-args constructor is always required.
                generateConstructors = true,
         
                // Whether getters and setters for properties should be generated if missing.
                generateGettersSetters = true
        )
        public class User {
          ...
        }

### 5.2 @Property(nameInDb = "USERNAME")非必需，作用是手动指定表名（默认是全大写+下划线分割）

### 5.3 @NotNull 非必需，但当属性是Long, Integer, Short, Byte等基本数据类型的包装类时，一定要加上


### 5.4 @Id(autoincrement = true)

### 5.5 @Transient

### 5.6 @Index(unique = true)  可做为主键的补充使用（慎用！）

### 5.7 @ToOne(joinProperty = "customerId") 从表关联一张主表

	public class Order {    
	  @Id 
	  private Long id;   
	
	  private long customerId;  
	
	  @ToOne(joinProperty = "customerId")  
	  private Customer customer;
	}
	
	@Entity
	public class Customer {    
	    @Id 
	    private Long id;
	}
### 5.8 @ToMany(referencedJoinProperty = "customerId")主表关联从表

	  @Entity
	  public class Customer {
	      @Id private Long id;
	
	      @ToMany(referencedJoinProperty = "customerId")
	      @OrderBy("date ASC")
	      private List<Order> orders;
	  }
		
	  注意：@ToMany(joinProperties = {@JoinProperty(name = "id", referencedName = "customerId")})	
	  与
      @ToMany(referencedJoinProperty = "customerId")
	  作用相同

	  @Entity
	  public class Order {
	      @Id private Long id;
	      private Date date;
	      private long customerId;
	  }

###  5.9@ToMany(joinProperties = {@JoinProperty(name = "tag", referencedName ="customerTag")	})关联非id字段的从表

	  @Entity
	  public class Customer {
	      @Id private Long id;
	      @Unique private String tag;
	
	      @ToMany(joinProperties = {
	              @JoinProperty(name = "tag", referencedName = "customerTag")
	      })
	      @OrderBy("date ASC")
	      private List<Site> orders;
	  }

	  @Entity
	  public class Order {
	      @Id private Long id;
	      private Date date;
	      @NotNull private String customerTag;
	  }


## 6. BeanType-Bean实体类的一个字段（）

## 7. BeanTypeConverter-



        