package com.ahuynh.muzimusicapp.ui.component.user.comment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.CommentAdapter
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ActivityCommentBinding
import com.ahuynh.muzimusicapp.ui.base.activity.BaseActivity
import com.ahuynh.muzimusicapp.ui.component.user.comment.menu.CommentMenu
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentActivity : BaseActivity<ActivityCommentBinding>(ActivityCommentBinding::inflate),
    CommentAdapter.OnCommentClicked {

    companion object {
        const val TAG = "CommentFragment"
    }

    private lateinit var currentSong: Song
    private var currentComment : Comment? = null

    private val viewModel by viewModels<CommentViewModel>()
    private val commentAdapter = CommentAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val song: Song? = intent.parcelable(Constants.SONG)



        if (song == null) finish()
        else currentSong = song

        currentComment = intent.parcelable(Constants.COMMENT)
        getData()
        handleUI()
        observe()

    }

    override fun getSnackbarView(): View {
        return binding.root
    }


    private fun observe() {
        viewModel.commentList.observe(this) {
            binding.rcyComment.visibility = View.VISIBLE
            if (it != null) {
                commentAdapter.submitList(it)
            }
            binding.refresh.isRefreshing = false


        }

        viewModel.totalComments.observe(this) {
            if (it > 0) {
                binding.tvNoComment.visibility = View.INVISIBLE
                binding.tvComment.text = getString(R.string.comment_count, it);
            } else {
                binding.tvNoComment.visibility = View.VISIBLE
                binding.tvComment.text = getString(R.string.comment)
            }
            binding.refresh.isRefreshing = false
        }


        viewModel.addCommentStatus.observe(this) {
            it?.let {
                binding.edtComment.text = null
                Toast.makeText(this, viewModel.mess, Toast.LENGTH_SHORT).show()
                getData()
            }

            viewModel.addCommentStatus.postValue(null)
        }
        viewModel.deleteCommentStatus.observe(this) {
            it?.let {
                Toast.makeText(this, viewModel.mess, Toast.LENGTH_SHORT).show()
                getData()
            }
            viewModel.deleteCommentStatus.postValue(null)
        }

        viewModel.updateCommentStatus.observe(this) {
            it?.let {
                Toast.makeText(this, viewModel.mess, Toast.LENGTH_SHORT).show()
                getData()
            }
            viewModel.updateCommentStatus.postValue(null)
        }

        viewModel.commentReply.observe(this) {
            if (it != null) {
                binding.tvName.text = it.user.username
                binding.reply.visibility = View.VISIBLE
                binding.btnCancle.visibility = View.VISIBLE
            } else {
                binding.reply.visibility = View.GONE
                binding.btnCancle.visibility = View.GONE
            }
        }

        viewModel.replyCommentStatus.observe(this) {
            it?.let {
                viewModel.commentReply.postValue(null)
                binding.edtComment.text = null
                Toast.makeText(this, viewModel.mess, Toast.LENGTH_SHORT).show()
                getData()
            }

            viewModel.replyCommentStatus.postValue(null)
        }


    }

    private fun handleUI() {
        binding.rcyComment.adapter = commentAdapter
        binding.btnDown.setOnClickListener {
            finish()
        }

        binding.edtComment.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val str = binding.edtComment.text.toString().trim()
                if (str.isNotEmpty()) {
                    viewModel.addCommentToSong(str, currentSong.id)
                }
            }
            true
        }

        binding.btnSend.setOnClickListener {
            val commentText = binding.edtComment.text.toString().trim()

            if (commentText.isNotEmpty()) {
                viewModel.commentReply.value?.let { reply ->
                    val commentParentId = reply.commentParentId ?: reply.id
                    viewModel.addReply(currentSong.id, commentParentId, commentText)
                } ?: run {
                    viewModel.addCommentToSong(commentText, currentSong.id)
                }
            }
        }
        binding.btnCancle.setOnClickListener {
            viewModel.commentReply.postValue(null)
            binding.edtComment.text = null
        }
        binding.refresh.setOnRefreshListener {
            getData()
        }


    }

    private fun getData() {
        viewModel.getCommentsOfSong(currentSong.id)
    }


    override fun openMenu(comment: Comment) {
        val menuBottomSheet = CommentMenu()
        menuBottomSheet.arguments = Bundle().apply {
            putParcelable(Constants.COMMENT, comment)
        }
        menuBottomSheet.show(supportFragmentManager, "MenuBottomSheet")
    }

    override fun replyComment(comment: Comment) {
        viewModel.commentReply.postValue(comment)
    }


}