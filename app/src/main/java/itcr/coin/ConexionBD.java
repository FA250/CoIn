package itcr.coin;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConexionBD {
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    
}
