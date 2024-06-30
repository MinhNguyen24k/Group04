package com.example.group04_readbookonline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TruyenDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Truyen";
    private static final int DATABASE_VERSION = 2;

    // Constructor
    public TruyenDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Category
        db.execSQL("CREATE TABLE Category (" +
                "CategoryID INTEGER PRIMARY KEY," +
                "CategoryName TEXT NOT NULL" +
                ");");

        // BẢNG Book
        db.execSQL("CREATE TABLE Book (" +
                "BookID INTEGER PRIMARY KEY," +
                "CategoryID INTEGER," +
                "Title TEXT NOT NULL," +
                "Price REAL," +
                "Description TEXT," +
                "Content TEXT," +
                "ImageName TEXT," +
                "FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID)" +
                ");");



        // Tạo bảng Role
        db.execSQL("CREATE TABLE Role (" +
                "RoleID INTEGER PRIMARY KEY," +
                "RoleName TEXT NOT NULL" +
                ");");

        // Tạo bảng Users
        db.execSQL("CREATE TABLE Users (" +
                "UserID INTEGER PRIMARY KEY," +
                "Username TEXT NOT NULL UNIQUE," +
                "Password TEXT NOT NULL," +
                "RoleID INTEGER," +
                "FOREIGN KEY (RoleID) REFERENCES Role(RoleID)" +
                ");");

        // Tạo bảng Orders
        db.execSQL("CREATE TABLE Orders (" +
                "OrderID INTEGER PRIMARY KEY," +
                "UserID INTEGER," +
                "OrderDate DATE," +
                "Status TEXT DEFAULT 'Chưa thanh toán'," +
                "FOREIGN KEY (UserID) REFERENCES Users(UserID)" +
                ");");

        // Tạo bảng OrderDetails
        db.execSQL("CREATE TABLE OrderDetails (" +
                "OrderDetailID INTEGER PRIMARY KEY," +
                "OrderID INTEGER," +
                "BookID INTEGER," +
                "Price REAL," +
                "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)," +
                "FOREIGN KEY (BookID) REFERENCES Book(BookID)" +
                ");");

        db.execSQL("INSERT INTO Category (CategoryID, CategoryName) VALUES (1, 'Truyện Cười');");
        db.execSQL("INSERT INTO Category (CategoryID, CategoryName) VALUES (2, 'Truyện Thiếu Nhi');");
        db.execSQL("INSERT INTO Category (CategoryID, CategoryName) VALUES (3, 'Tiểu thuyết');");
        db.execSQL("INSERT INTO Category (CategoryID, CategoryName) VALUES (4, 'Tình cảm');");
        db.execSQL("INSERT INTO Category (CategoryID, CategoryName) VALUES (5, 'Triết học');");



        db.execSQL("INSERT INTO Role (RoleID, RoleName) VALUES (1, 'admin');");
        db.execSQL("INSERT INTO Role (RoleID, RoleName) VALUES (2, 'user');");

        // Thêm tài khoản admin vào bảng Users
        db.execSQL("INSERT INTO Users (UserID, Username, Password, RoleID) VALUES (1, 'admin', 'admin', 1);");
        db.execSQL("INSERT INTO Users (UserID, Username, Password, RoleID) VALUES (2, 'minh', 'minh', 2);");




        // Số đỏ
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (1, 'Số đỏ', 10000, 'Số đỏ là một tiểu thuyết văn học của nhà văn Vũ Trọng Phụng, đăng ở Hà Nội báo từ số 40 ngày 7 tháng 10 1936 và được in thành sách lần đầu vào năm 1938.', 'Lúc ấy vào độ 3 giờ chiều, một ngày thứ năm.\n" +
                "\n" +
                "Trong khu sân quần mà bên ngoài là những hàng ruối kín mít, chỉ có một sân hữu là được hai người Pháp dùng đến. Hai đứa trẻ nhỏ tuổi uể oải đi nhặt những quả bóng để ném cho hai người Tâỵ Mồ hôi ướt đầm áo, hai người này cũng chơi uể oải như những nhà thể thao bất đắc dĩ khác.\n" +
                "\n" +
                "- xanh ca! (1)\n" +
                "\n" +
                "- xanh xít! (2)\n" +
                "\n" +
                "Những câu hô như vậy chen lẫn những tiếng bồm bộp của những quả ban bị đánh đi, như giữ nhịp cho khúc âm nhạc của mấy vạn con ve sầu.\n" +
                "\n" +
                "Ngoài đường ở vệ hè, một người bán nước chanh, ngồi chồm chỗm trên càng xe, đương nói chuyện với một người bạn đồng nghiệp.\n" +
                "\n" +
                "- Quái, thứ năm gì mà vắng thế!\n" +
                "\n" +
                "- Chốc nữa họ mới lại chứ? Bây giờ mới hơn ba giờ. Từ hôm nay trở đi, họ tập gấp, chắc ngày nào cũng phải luyện chứ chả cứ thứ năm thứ bảy hay chủ nhật...\n" +
                "\n" +
                "- Thế à? Sao biết?\n" +
                "\n" +
                "- Mê đi! Ba bốn tháng nữa, đức vua ra đây, lại còn gì! Chuyến này sẽ có cúp oai ghê... Các anh các chị gọi là tập mửa mật.\n" +
                "\n" +
                "Trên hè, dưới bóng cây gạo, một ông thầy số đã có tuổi ngồi bình tĩnh nhìn cái cháp, nghiên mực, miếng son, ống bút, với mấy lá số tử vi mẫu, thỉnh thoảng lại ngáp một cái như một nhà triết học chân chính. Cách đấy mười bước, Xuân Tóc Đỏ ngồi tri kỷ với một chị hàng mía. Thương mại Không! Ấy là một cuộc tình duyên, với, hơn nữa - theo lối gọi của những ông làm báo - một cuộc tình duyên của Bình dân (chữ B hoa).\n" +
                "\n" +
                "Là vì Xuân Tóc Đỏ cứ sấn sổ đưa tay toan cướp giật ái tình...\n" +
                "\n" +
                "-... Cứ ỡm ờ mãi!\n" +
                "\n" +
                "- Xin một tị! Xin một tị tỉ tì ti thôi!\n" +
                "\n" +
                "- Khỉ lắm nữa!\n" +
                "\n" +
                "- Lẳng lơ thì cũng chẳng mòn...\n" +
                "\n" +
                "- Thật đấy. Chính chuyên cũng chẳng sơn son để thờ! Nhưng này! Duyên kia ai đợi mà chờ? Tình kia ai tưởng mà tơ tưởng tình? Hàng đã ế bỏ mẹ ra thế này này, mua chẳng mua giúp lại chỉ được cái bộ ếm...\n" +
                "\n" +
                "Xuân Tóc đỏ đứng phăng lên, anh hùng mà nói dỗi:\n" +
                "\n" +
                "- Đây không cần!\n" +
                "\n" +
                "Chị hàng mía lườm dài một cái, cong cớn:\n" +
                "\n" +
                "- Không cần thì cút vào trong ấy có được không?\n" +
                "\n" +
                "Xuân Tóc Đỏ lại cười hí hí như ngựa rồi ngồi xuống...\n" +
                "\n" +
                "- Nói đùa đấy, chứ đây mà lại chả cần đấy thì đấy cần đếch gì đây. Thôi đi, làm bộ vừa vừa chứ... Bán một xu nàọ\n" +
                "\n" +
                "- Ừ! Ứ! Đưa tiền ngay ra đây xem!\n" +
                "\n" +
                "Rút ở túi quần sau một cái mù soa, cởi một nút buộc như một cái tai lợn, Xuân Tóc Đỏ đập đồng hào ván xuống thềm gạch xi măng đánh keng một cái rất oanh liệt.\n" +
                "\n" +
                "Trong khi chị hàng mía cầm một tấm để róc vỏ thì Xuân lải nhải tự cổ động cho mình:\n" +
                "\n" +
                "- Năm hào còn hai đấy! Tối hôm qua mất ba hào. Thết bạn cẩn thận... Hai hào vé đi tuần trong Hý viện rồi lại bát phở tái năm. Chơi thế mới chánh chứ? Công tử bột thì cũng chúa đến thế là cùng... Ấy ăn tiêu rộng như thế mới chết! Đây bảo đấy về đây phải lo thì khỏi ăn chơi, thì đấy mãi chả nghe!', 'sodo');");


        // Ai Ăn Cắp Nỏ Thần Của An Dương Vương?
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (1, 'Ai Ăn Cắp Nỏ Thần Của An Dương Vương?', 5000, 'Truyện cười tiếu lâm.', 'Thầy giáo hỏi:\n" +
                "\n" +
                "- Ai ăn cắp nỏ thần của An Dương Vương?\n" +
                "\n" +
                "Cả lớp im lặng. Thầy giáo chỉ 1 học sinh:\n" +
                "\n" +
                "- Em biết ai ăn cắp nỏ thần của An Dương Vương không?\n" +
                "\n" +
                "Học sinh sợ sệt:\n" +
                "\n" +
                "- Dạ không phải em.\n" +
                "\n" +
                "Vừa lúc đó ông hiệu trưởng đi ngang.\n" +
                "\n" +
                "Thầy giáo phân bua:\n" +
                "\n" +
                "- Anh xem, học trò bây giờ tệ quá. Tui hỏi ai ăn cắp nỏ thần của An Dương Vương mà không ai biết.\n" +
                "\n" +
                "Thầy hiệu trưởng gật gù:\n" +
                "\n" +
                "- Thôi anh nói anh Vương làm bản báo cáo rồi tui nói ban giám hiệu xuất quĩ đền cho. Đừng làm rùm beng mang tiếng chết', 'nothan');");


        // Bàn có 5 chỗ ngồi
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (2, 'Bàn có 5 chỗ ngồi', 5000, 'Hãy đọc để cùng gặp những nhân vật rất dễ thương, hoặc phải vượt qua những hoàn cảnh khó khăn, hoặc sẵn lòng chia sẻ với bạn bè, người thân… Thậm chí cả những nhân vật tạm gọi là “phe xấu” với nhiều trò đùa nghịch… cũng mang dáng dấp hồn nhiên. Tất cả hướng về Chân - Thiện - Mỹ.', 'Năm nay tôi lên lớp tám. Như vậy là tôi sắp sửa trở thành người lớn rồi. Oai thiệt là oai ! Tôi không nói dóc đâu. Chính thầy Dân, giáo viên chủ nhiệm của lớp tôi nói như thế.Năm ngoái chúng tôi không học thầy Dân. Các anh chị lớp trên người thì bảo thầy Dân khó, kẻ thì nói thầy Dân dễ, không biết đường nào mà tin. Nhưng hôm khai trường, ngay lần gặp đầu tiên, tôi thấy thầy không có vẻ gì là \"hắc\" cả. Thầy nói chuyện với lớp tôi bằng một giọng đầm ấm, thân mật :- Như các em đã biết, năm nay thầy làm chủ nhiệm kiêm phụ trách chi đội lớp các em. Từ từ rồi thầy trò mình sẽ làm quen với nhau. Thầy tin rằng các em sẽ tự giác học tập tốt, trau giồi đạo đức, rèn luyện thân thể và chấp hành đúng nội quy của trường ta. Bởi vì năm nay các em không còn bé bỏng gì nữa, đã chuẩn bị trở thành người lớn rồi, toán các em sẽ làm quen với quỹ tích, văn các em sẽ bắt đầu học nghị luận. Những điều đó hoàn toàn khác xa với chương trình lớp bảy...Thầy nói chưa hết mà cả lớp đã vỗ tay rần rần. Nghe nói mình sắp sữa trở thành người lớn, đứa nào cũng khoái. Tôi cũng vậy. Thầy còn nói nhiều nhưng tôi chẳng nhớ gì ngoài khoản \"người lớn\" đó.Về nhà, tôi khoe ngay với thằng Tin, em tôi. Tôi vỗ vai nó, lên giọng :- Tao năm nay là người lớn rồi đó nghe mày !Thầy Dân nói là chúng tôi chuẩn bị làm người lớn thôi nhưng tôi cứ muốn làm người lớn ngay cho oai.Thằng Tin là chúa hay cãi. Không bao giờ nói đồng ý với tôi một điều gì. Lần này cũng vậy, nó nheo mắt :- Anh mà là người lớn ?- Chớ gì nữa !- Người lớn sao không có râu ?- Tao cần quái gì râu !Thằng Tin cười hì hì :- Vậy thì anh cũng vẫn còn là trẻ con giống như em thôi.Tôi \"xì\" một tiếng :- Mày làm sao giống tao được, đừng có dóc ! Chính thầy Dân nói tụi tao là người lớn nè ! Bởi vì chương trình lớp tám cái gì cũng khó hết, học hết cơm hết gạo chưa chắc đã hiểu.Thằng Tin nhìn tôi với vẻ nghi ngờ :- Khó dữ vậy hả ?Tôi nghiêm mặt :- Bộ tao nói chơi với mày sao !.', 'bannamchongoi');");

        // Giông Tố
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (3, 'Giông Tố', 155000, 'Một tiểu thuyết hay của nhà văn Vũ Trọng Phụng', 'Mặt trăng rất to và rất tròn, chiếu vằng vặc... cánh đồng lúa chín như một tấm thảm vàng. Con đường quan lộ rải nhựa, như một con rắn bóng nhễ nhại, nằm uốn khúc trên tấm thảm ấy. Những làng mạc xa xa hiện ra những nét vẽ thẳng đen sì.\n" +
                "\n" +
                "Đó là vào tháng mười, năm 1932.\n" +
                "\n" +
                "Giữa lúc đêm khuya tịch mịch ấy, trên con đường quan lộ, mà thỉnh thoảng mới có một vài cây xoan không lá khẳng khiu và tiều tụy như thứ cây trong những bức họa về “cảnh chết”, một chiếc xe hòm phăng phăng chạy hết tốc lực, thân xe chỉ là một cái chấm đen bóng, còn hai ngọn đèn sáng quắc chiếu dài hàng nửa cây số thì như hai cái tên vun vút bay dưới ánh trăng.\n" +
                "\n" +
                "Xe đương phăng phăng chạy thì đến gần một chỗ ngoặt mà bên đường có một lớp quán gạch và một cây đa cổ thụ, bỗng dần dần chậm lại, rồi đứng hẳn.\n" +
                "\n" +
                "Khi xe đã đứng dừng lại lâu rồi, người ta còn thấy sự cố sức của người tài xế mở máy sình sịch mấy lần nữa mà xe vẫn không nhúc nhích được một ly. Rồi thì từ xe bước xuống, hai người tài xế hấp tấp ra mũi xe, lật miếng sắt che máy ra, loay hoay kiểm điểm bộ máy. Trong khi hai người chưa tìm được cỗ xe chết vì lẽ gì, thì từ trong hòm kính thấy đưa ra một câu hỏi gắt rất ngắn, nhưng cũng đủ làm cho cả hai run lập cập.\n" +
                "\n" +
                "- Thế nào?\n" +
                "\n" +
                "Vài phút im lặng, rồi người tài xế chính ấp úng đáp:\n" +
                "\n" +
                "- Bẩm quan, con đã thấy rồi. Cái ống cao su dẫn ét xăng có một đoạn nát nhủn, đến nỗi xăng chảy cả ra ngoài nhưng mà xuống không thoát.\n" +
                "\n" +
                "- ...! Sao không liệu mà thay vào cái chuyến chữa hôm nọ đi?...\n" +
                "\n" +
                "Dứt lời “chửi”, “quan” bước xuống xe và sập cửa xe rất mạnh để tỏ ý giận dữ. Đó là một người gần 50 thân thể vạm vỡ, hơi lùn, trước mặt có một cặp kính trắng gọng vàng, trên môi có một ít râu lún phún kiểu tây, cái mũ dạ đen hình quả dưa, cái áo đen bóng một khuy, cái quần đen, rọc trắng, đôi giày láng mũi nhọn và bóng lộn, làm cho lão có cái vẻ sang trọng mà quê kệch, cái vẻ rất khó tả của những anh trọc phú học làm người văn minh...\n" +
                "\n" +
                "Tài xế chính và phụ, cả hai đều sợ hãi lắm, cứ việc châu đầu vào cái hòm máy, lúc đánh diêm soi, lúc sờ soạng như\n" +
                "\n" +
                "xẩm tìm gậy, chứ không dám quay lại nhìn đến ông chủ, lúc ấy đứng dạng háng giữa đường, hai tay khoanh trước ngực, đầu hơi cúi xuống phía trước mặt, cặp mắt gườm gườm hứa một sự trừng trị đáng sợ. Bị chủ mắng tài xế chính khẽ quát người phụ:\n" +
                "\n" +
                "- Cầm lấy cái mùi xoa này, buộc nối vào hai đầu dây cao su! Mau lên! Mà quấn rõ chặt cho nó thật kín chứ!\n" +
                "\n" +
                "Rồi người tài xế chính lại lên ngồi mở máy thử. Cái xe kêu sình sịch một lúc lâu rồi lại thôi. Mấy bận đều thế cả, hai người càng hấp tấp bao nhiêu, càng gia công vất vả bao nhiêu thì cái xe càng bướng bỉnh, càng ỳ ra bấy nhiêu. Lão chủ cười nhạt mà rằng:\n" +
                "\n" +
                "- Tội chúng mày đáng chết cả đó, các con ạ!', 'giongto');");



        // Mắt Biết
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (4, 'Mắt Biết', 50000, 'Khoảnh khắc của mắt, sự biểu cảm và ngôn ngữ cơ thể trong mối quan hệ, giúp độc giả hiểu rõ hơn về sự giao tiếp phi lời.', 'Từ cách nhìn vào đôi mắt đến ngôn ngữ cơ thể, cuốn sách giúp độc giả đọc được những dấu hiệu tinh tế trong mối quan hệ và cải thiện giao tiếp.', 'anhtinhcam4');");

        // Tôi Đã Bắt Tình Yêu Lộ Mặt
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (4, 'Tôi Đã Bắt Tình Yêu Lộ Mặt', 55000, 'Một cuộc phiêu lưu đầy hấp dẫn và lãng mạn, kể về hành trình của nhân vật chính trong việc khám phá và bắt lấy tình yêu.', 'Tác phẩm này kết hợp giữa yếu tố lãng mạn và sự hồi hộp của một cuộc phiêu lưu, khi nhân vật chính dấn thân vào hành trình đầy thách thức để tìm thấy và giữ lấy tình yêu lộ mặt.', 'anhtinhcam5');");

        // Chí Phèo
        db.execSQL("INSERT INTO Book (CategoryID, Title, Price, Description, Content, ImageName) VALUES (3, 'Chí Phèo', 2000, 'Một cuộc phiêu lưu đầy hấp dẫn và lãng mạn, kể về hành trình của nhân vật chính trong việc khám phá và bắt lấy tình yêu.', 'Hắn vừa đi vừa chửi. Bao giờ cũng thế, cứ rượu xong là hắn chửi. Bắt đầu chửi trời. Có hề gì? Trời có của riêng nhà nào? Rồi hắn chửi đời. Thế cũng chẳng sao: đời là tất cả nhưng chẳng là ai. Tức mình hắn chửi ngay tất cả làng Vũ Ðại. Nhưng cả làng Vũ Ðại ai cũng nhủ, \"Chắc nó trừ mình ra!\" Không ai lên tiếng cả. Tức thật! Ồ! Thế này thì tức thật! Tức chết đi được mất! Ðã thế, hắn phải chửi cha đứa nào không chửi nhau với hắn. Nhưng cũng không ai ra điều. Mẹ kiếp! Thế thì có phí rượu không? Thế thì có khổ hắn không? Không biết đứa chết mẹ nào đẻ ra thân hắn cho hắn khổ đến nông nỗi này? A ha! Phải đấy, hắn cứ thế mà chửi, hắn chửi đứa chết mẹ nào đẻ ra thân hắn, đẻ ra cái thằng Chí Phèo! Hắn nghiến răng vào mà chửi cái đứa đã đẻ ra Chí Phèo. Nhưng mà biết đứa nào đã đẻ ra Chí Phèo? Có trời mà biết! Hắn không biết, cả làng Vũ Ðại cũng không ai biết...\n" +
                "\n" +
                "Một anh đi thả ống lươn, một buổi sáng tinh sương đã thấy hắn trần truồng và xám ngắt trong một váy đụp để bên một lò gạch bỏ không, anh ta rước lấy và đem về cho một người đàn bà góa mù. Người đàn bà góa mù này bán hắn cho một bác phó cối không con, và khi bác phó cối này chết thì hắn bơ vơ, hết đi ở cho nhà này lại đi ở cho nhà nọ. Năm hai mươi tuổi, hắn làm canh điền cho ông lý Kiến, bấy giờ cụ bá Kiến, ăn tiên chỉ làng. Hình như có mấy lần bà ba nhà ông lý, còn trẻ lắm mà lại hay ốm lửng, bắt hắn bóp chân, hay xoa bụng, đấm lưng gì đấy. Người ta bảo ông lý ra đình thì hách dịch, cả làng phải sợ, mà về nhà thì lại sợ cái bà ba còn trẻ này. Người bà ấy phốp pháp, má bà ấy hây hây, mà ông lý thì hay đau lưng lắm; những người có bệnh đau lưng hay sợ vợ mà chúa đời là khoẻ ghen. Có người bảo ông lý ghen với anh canh điền khoẻ mạnh mà sợ bà ba không dám nói. Có người thì bảo anh canh điền ấy được bà ba quyền thu quyền bổ trong nhà tin cẩn nên lấy trộm tiền trộm thóc nhiều. Mỗi người nói một cách. Chẳng biết đâu mà lần. Chỉ biết một hôm Chí bị người ta giải huyện rồi biệt tăm đến bảy, tám năm rồi một hôm, hắn lại lù lù ở đâu lần về. Hắn về lớp này trông khác hẳn, mới đầu chẳng ai biết hắn là ai. Trông đặc như thằng sắng cá! Cái đầu thì trọc lốc, cái răng cạo trắng hớn, cái mặt thì đen mà rất câng câng, hai mắt gườm gườm trông gớm chết! Hắn mặc quần áo nái đen với áo Tây vàng. Cái ngực phanh, đầy những nét chạm trổ rồng, phượng với một ông thầy tướng cầm chuỳ, cả hai cánh tay cũng thế. Trông gớm chết!\n" +
                "\n" +
                "Hắn về hôm trước hôm sau đã thấy ngồi ở chợ uống rượu với thịt chó suốt từ trưa cho đến xế chiều. Rồi say khướt, hắn xách một cái vỏ chai đến cổng nhà bá Kiến, gọi tận tên tục ra mà chửi. Cụ bá không có nhà. Thấy điệu bộ hung hăng của hắn, bả cả đùn bà hai, bà hai thúc bà ba, bà ba gọi bà tư, nhưng kết cục chẳng bà nào dám ra nói với hắn một vài lời phải chăng. Mắc phải cái thằng liều lĩnh quá, nó lại say rượu, tay nó lại nhăm nhăm cầm một cái vỏ chai, mà nhà lúc ấy toàn đàn bà cả... Thôi thì cứ đóng cái cổng cho thật chặt, rồi mặc thây cha nó, nó chửi thì tai liền miệng ấy, chửi rồi lại nghe. Thành thử chỉ có ba con chó dữ với một thằng say rượụ.. Thật là ầm ỹ! Hàng xóm phải một bữa điếc tai, nhưng có lẽ trong bụng thì họ hả: xưa nay họ mới chỉ được nghe bà cả, bà hai, bà ba, bà tư nhà cụ bá chửi người ta, bây giờ họ mới được nghe người ta chửi lại cả nhà cụ bá. Mà chửi mới sướng miệng làm sao! Mới ngoa ngắt làm sao! Họ bảo nhau: phen này cha con thằng bá Kiến đố còn dám vác mặt đi đâu nữa! Mồ mả tổ tiên đến lộn lên mất. Cũng có người hiền lành hơn bảo, \"Phúc đời nhà nó, chắc ông lý không có nhà...\". Ông lý đây là ông lý Cường, con giai cụ bá nổi tiếng là hách dịch, coi người như rơm rác. Phải ông lý Cường thử có nhà xem nào! Quả nhiên họ nói có sai đâu! Ðấy, có tiếng người sang sảng quát, \"Mày muốn lôi thôi gì? Cái thằng không cha không mẹ này! Mầy muốn lôi thôi gì?...\" Ðã bảo mà! Cái tiếng quát tháo kia là tiếng lý Cường. Lý Cường đã về! Lý Cường đã về! Phải biết... A ha! Một cái tát rất kêu. Ôi! Cái gì thế nàỷ Tiếng đấm, tiếng đá nhau bình bịch. Thôi cứ gọi là tan xương! Bỗng \"choang\" một cái, thôi phải rồi, hẳn đập cái chai vào cột cổng... Ồ hắn kêu! Hắn vừa chửi vừa kêu làng như bị người ta cắt họng. Ồ hắn kêu!', 'chipheo');");





    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
