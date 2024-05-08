package com.ahuynh.muzimusicapp.service


//class NotificationService : FirebaseMessagingService() {
//
//    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)
//        val title = message.data["title"]
//        val content = message.data["content"]
//        val action = message.data["action"]
//
//        sendNotification(title, content, action)
//    }
//
//    private fun sendNotification(title: String?, content: String?, action: String?) {
//        val intent = Intent(this, TricksActivity::class.java).apply {
//            putExtra(ACTION, action)
//        }
//
//        val pendingIntent = TaskStackBuilder.create(this).run {
//            addNextIntentWithParentStack(intent)
//            getPendingIntent(
//                0,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
//            )
//        }
//
//        val icon = BitmapFactory.decodeResource(
//            this.resources,
//            R.drawable.note
//        )
//
//
//        val notification =
//            NotificationCompat.Builder(this, MuziMusicApplication.NOTIFICATION_CHANNEL_ID)
//                .setSmallIcon(R.drawable.note)
//                .setContentTitle(title)
//                .setContentText(content)
//                .setContentIntent(pendingIntent)
//                .setLargeIcon(icon)
//                .setStyle(
//                    NotificationCompat.BigPictureStyle().bigPicture(icon)
//                )
//                .setAutoCancel(true)
//                .build()
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
//        notificationManager?.notify(2, notification)
//    }
//
//    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//    }
//}