package com.cuong.haui.computershop.ui.addProduct

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityAddProductBinding
import com.cuong.haui.computershop.model.Products
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class addProductActivity : BaseActivity<ActivityAddProductBinding>() {
    private val btnChoose: Button? = null
    private  var btnUpload:Button? = null
    private val imageView: ImageView? = null

    private var filePath: Uri? = null

    private val PICK_IMAGE_REQUEST = 71
    private lateinit var database : DatabaseReference
    var storageReference : StorageReference = FirebaseStorage.getInstance().getReference()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun initCreate() {
        binding.btnChoose.setOnClickListener{
            chooseImage()
        }

        binding.btnUpload.setOnClickListener{
            uploadImage()
        }


    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath!!)
                binding.imgView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadImage() {
        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val ref = storageReference.child("images/" + UUID.randomUUID().toString())
            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this@addProductActivity, "Uploaded", Toast.LENGTH_SHORT).show()

                    val downloadUri: Task<Uri> = it.storage.downloadUrl
                    downloadUri.addOnSuccessListener {
                        var imageLink = it.toString()
                        Log.d("cuongnm", "uploadImage: $imageLink")

                        //val product_id: Int,val product_name:String?="",val price_new: Int,val price_old: Int,val thumbnail_url:String?=""
                        //               ,val description:String?="",val cpu: String?="",val ram: String?="",val hard_drive: String?="",val graphics:String?="",
                        //               val screen:String?="",val precent_discount: Int,val created_at:String?="",val update_at:String?="",val deleted: Int,
                        //               val current_quantity: Int,val warranty_period: Int,val category_id: Int
                        val product_id = 1
                        val product_name = binding.productName.text.toString()
                        val price_new = binding.priceNew.text.toString().toInt()
                        val price_old = binding.priceOld.text.toString().toInt()
                        val thumbnail_url = imageLink
                        val description = binding.description.text.toString()
                        val cpu = binding.cpu.text.toString()
                        val ram = binding.ram.text.toString()
                        val hard_drive = binding.hardDrive.text.toString()
                        val graphics = binding.graphicsCard.text.toString()
                        val screen = binding.screen.text.toString()
                        val precent_discount = binding.precentDiscount.text.toString().toInt()
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                        val current = LocalDateTime.now().format(formatter)
                        val created_at = current.toString()
                        val update_at = current.toString()
                        val deleted = 1
                        val current_quantity = binding.currentQuantity.text.toString().toInt()
                        val warranty_period = binding.warrantyPeriod.text.toString().toInt()
                        val category_id = 1


                        database = FirebaseDatabase.getInstance().getReference("Products")
                        val products = Products(product_id,product_name,price_new,price_old,thumbnail_url,description,cpu,ram,hard_drive,graphics,screen,precent_discount,created_at,update_at,deleted,current_quantity,warranty_period,category_id)
                        database.child(product_id.toString()).setValue(products).addOnSuccessListener {

                            /*binding.firstName.text.clear()
                            binding.lastName.text.clear()
                            binding.age.text.clear()
                            binding.userName.text.clear()*/

                            Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()

                        }.addOnFailureListener{

                            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


                        }

                    }.addOnFailureListener {
                        Log.d("cuongnm", "uploadImage: fail")
                    }
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this@addProductActivity, "Failed " + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityAddProductBinding{
        return ActivityAddProductBinding.inflate(inflater)
    }

}
/*fun insert_data(view : View){
      db = FirebaseDatabase.getInstance().getReference("items")
      val item = itemDs(sImage)
      val databaseReference = FirebaseDatabase.getInstance().reference
      val id = databaseReference.push().key
      db.child(id.toString()).setValue(item).addOnSuccessListener {
          sImage = ""
          Toast.makeText(this,"data inserted",Toast.LENGTH_SHORT).show()
      }.addOnFailureListener {
          Toast.makeText(this,"data Not inserted",Toast.LENGTH_SHORT).show()
      }
  }
  fun insert_Img(view: View){
      var myfileintent = Intent(Intent.ACTION_GET_CONTENT)
      myfileintent.setType("image/*")
     // ActivityResultLauncher.launch(myfileintent)

  }
  private val ActivityResultLauncher = registerForActivityResult<Intent,ActivityResult>(
      ActivityResultContracts.StartActivityForResult()
  ){ result: ActivityResult ->
      if (result.resultCode == RESULT_OK){
          val uri = result.data!!.data
          try {
              val inputStream = contentResolver.openInputStream(uri!!)
              val myBitmap = BitmapFactory.decodeStream(inputStream)
              val stream = ByteArrayOutputStream()
              myBitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
              val bytes = stream.toByteArray()
              sImage = Base64.encodeToString(bytes,Base64.DEFAULT)
              binding.imageView.setImageBitmap(myBitmap)
              inputStream!!.close()
              Toast.makeText(this,"Image Selected",Toast.LENGTH_SHORT).show()
          }catch (ex: Exception){
              Toast.makeText(this,ex.message.toString(),Toast.LENGTH_LONG).show()
          }

      }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.data!!
            binding.imageView.setImageURI(imageUri)
        }
    }
    fun uploadToFirebase(uri: Uri){
        var fileRef = reference.child(System.currentTimeMillis().toString() + "." + getFileExtension(uri))
        fileRef.putFile(uri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener {

            }

        }.addOnProgressListener {
            progerssBar.visibility

        }.addOnFailureListener{
            Toast.makeText(this,"uploading Failed ",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFileExtension(uri: Uri): Any? {
        var cr = contentResolver
        var mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(uri))
    }

binding.imageView.setOnSafeClick {
            var galleryIntent = Intent()
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT)
            galleryIntent.setType("image/*")
            startActivityForResult(galleryIntent,2)

        }
        binding.btnUpload.setOnSafeClick {
            if(imageUri!= null){
                uploadToFirebase(imageUri)
            }
        }
        binding.btnShowall.setOnSafeClick {

        }
  }*/