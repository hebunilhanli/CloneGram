package com.example.hebunilhanli.clonegram.StartingProcess;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hebunilhanli.clonegram.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class add_post extends AppCompatActivity {

    public static final String ARG_SELECTED_FILE = "selected_file";
    EditText commentText;
    EditText fullname;
    ImageView imageView;
    FirebaseDatabase database;
    DatabaseReference myRef,newReference2;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    String uuidString2;
    Boolean like = false;
    String url;
    String fullnames;
    ImageView liketype;
    String uuid2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);


        commentText = findViewById(R.id.comment);
        imageView = (ImageView) findViewById(R.id.imageView);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        fullname = findViewById(R.id.nameSurname);





        Log.e("Check It", (liketype == null) ? "liketype is null " : "liketype not null");




        /*------------------------------------------------------------*/




        byte[] bytes = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        imageView.setImageBitmap(bitmap);





    }


    public void share(View view) {

        UUID uuidImage = UUID.randomUUID();// image id leri karismasin diye random

        String imageName = "images/"+ uuidImage + ".jpg";

        StorageReference storageReference = mStorageRef.child(imageName);//Imagename adinda dal çikariyoruz

        storageReference.putFile(MainActivity.selected).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {//Eger dosya yükleme basarili ise




                String downloadURL = taskSnapshot.getDownloadUrl().toString();

                FirebaseUser user = mAuth.getCurrentUser();
                String userEmail = user.getEmail().toString();


                String userComment = commentText.getText().toString();









                UUID uuids = UUID.randomUUID();
                String users = uuids.toString();

                final String uuid = user.getUid();
                final String uuidString = "user_" + uuid.toString();
                uuidString2 = uuidString + uuids;

                uuid2 = "user_" + users;


                myRef.child("Users").child(uuidString).child(uuid2).child("comment").setValue(userComment);
                myRef.child("Users").child(uuidString).child(uuid2).child("downloadurl").setValue(downloadURL);


                myRef.child("All Posts").child(uuidString2).child("useremail").setValue(userEmail);
                myRef.child("All Posts").child(uuidString2).child("comment").setValue(userComment);
                myRef.child("All Posts").child(uuidString2).child("downloadurl").setValue(downloadURL);
                myRef.child("All Posts").child(uuidString2).child("LikeCount").setValue(like.toString());


                newReference2 = database.getReference("Users Informations").child("user_" + user.getUid());
                newReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {



                       url = dataSnapshot.child("DownloadURL").getValue(String.class);
                       fullnames = dataSnapshot.child("Fullname").getValue(String.class);

                        myRef.child("All Posts").child(uuidString2).child("ProfilePP").setValue(url);
                        myRef.child("All Posts").child(uuidString2).child("Fullname").setValue(fullnames);
                        myRef.child("Users").child(uuidString).child(uuid2).child("Fullname").setValue(fullnames);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Toast.makeText(getApplicationContext(),"Post Shared",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {// Eger paylasim basarisiz olursa
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });




    }
}
