package com.ahuynh.muzimusicapp.ui.component.user.notification.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import coil.load
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.data.model.Notification
import com.ahuynh.muzimusicapp.databinding.FragmentNotificationMenuBinding
import com.ahuynh.muzimusicapp.ui.base.dialog.ConfirmDialog
import com.ahuynh.muzimusicapp.ui.component.user.notification.NotificationViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils.loadImage
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationMenu : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "NotificationMenu"
    }

    private lateinit var currentNotification: Notification
    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentNotificationMenuBinding
    private val viewModel by viewModels<NotificationViewModel>({ requireActivity() })
    private val menuAdapter = MenuAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notification: Notification? = arguments?.parcelable(Constants.NOTIFICATION)
        if (notification == null) dismiss()
        else currentNotification = notification
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotificationById(currentNotification.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.rcyMenu.adapter = menuAdapter
        binding.tvTitle.text = currentNotification.title
        binding.tvContent.text = currentNotification.content
        binding.imvAvatar.setImageResource(R.drawable.ic_spotify_white)
        viewModel.currentNotification.observe(viewLifecycleOwner) {
            initData(it.status == "READ")
        }
    }

    private fun initData(isRead: Boolean) {
        itemMenuList.clear()
        itemMenuList.add(
            ItemMenu(
                getString(R.string.delete_notification),
                R.drawable.ic_delete,
                ItemMenuName.DELETE
            )
        )
        if (isRead) {
            itemMenuList.add(
                ItemMenu(
                    getString(R.string.mark_as_unread),
                    R.drawable.ic_hearted,
                    ItemMenuName.READ
                )
            )
        } else {
            itemMenuList.add(
                ItemMenu(
                    getString(R.string.mark_as_read),
                    R.drawable.ic_heart_small,
                    ItemMenuName.READ
                )
            )
        }
        menuAdapter.submitList(itemMenuList.toList())
    }

    private fun observeViewModel() {
        viewModel.markNotificationAsReadStatus.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    dismiss()
                }
            }
            viewModel.markNotificationAsReadStatus.postValue(null)
        }


    }

    override fun onMenuClicked(menu: ItemMenu) {
        when (menu.type) {
            ItemMenuName.READ -> {
                viewModel.markNotificationAsRead(currentNotification.id)
                dismiss()
            }

            ItemMenuName.DELETE -> {
                ConfirmDialog(
                    requireContext(),
                    title = "Confirm Delete Notification",
                    message = "Do you want to delete this notification",
                    negativeButtonTitle = "CANCEL",
                    positiveButtonTitle = "DELETE",
                    callback = object : ConfirmDialog.ConfirmCallBack {
                        override fun negativeAction() {
                            dismiss()
                        }

                        override fun positiveAction() {
                            viewModel.deleteNotification(currentNotification.id)

                            viewModel.deleteNotificationStatus.observe(viewLifecycleOwner) {
                                it?.let {
                                    if (it) {
                                        dismiss()
                                    }
                                }
                                viewModel.deleteNotificationStatus.postValue(null)


                            }

                        }

                    }
                ).show()
            }

            else -> {
            }
        }
    }
}