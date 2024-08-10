package com.ahuynh.muzimusicapp.ui.component.user.notification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.NotificationAdapter
import com.ahuynh.muzimusicapp.data.model.Notification
import com.ahuynh.muzimusicapp.databinding.FragmentNotificationBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.component.notification_common.NotificationCommonActivity
import com.ahuynh.muzimusicapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseDialogBottomSheetFragment(),
    NotificationAdapter.OnNotificationClicked {

    companion object {
        const val TAG = "NotificationFragment"
    }

    private val notificationAdapter = NotificationAdapter(this)
    private val viewModel by viewModels<NotificationViewModel>({ requireActivity() })
    private lateinit var notificationsList: ArrayList<Notification>
    private lateinit var binding: FragmentNotificationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun getData() {
        viewModel.getAllNotifications()
    }

    private fun observeViewModel() {
        viewModel.notificationList.observe(viewLifecycleOwner) {
            notificationAdapter.submitList(it)
            notificationsList = it as ArrayList<Notification>
            if(it.isEmpty()){
                binding.btnDeleteAllNotification.visibility = View.GONE
            } else
                binding.btnDeleteAllNotification.visibility = View.VISIBLE
            binding.rcyNotification.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
            binding.tvNoNotification.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.GONE
        }


    }

    private fun setupUI() {
        binding.rcyNotification.adapter = notificationAdapter
        binding.btnBack.setOnClickListener {
            dismiss()
        }
        binding.btnDeleteAllNotification.setOnClickListener {
            viewModel.deleteAllNotifications()
        }
    }


    override fun onNotificationClicked(notification: Notification) {
        val intent = Intent(requireActivity(), NotificationCommonActivity::class.java).apply {
            putExtra(Constants.TYPE, notification.type)
            putExtra(Constants.SONG_ID, notification.songId)
            putExtra(Constants.COMMENT_ID, notification.songId)
        }
        startActivity(intent)


    }

    override fun openMenu(notification: Notification) {
        val action =
            NotificationFragmentDirections.actionNotificationFragmentToNotificationMenu(notification)
        findNavController().navigate(action)
    }
}