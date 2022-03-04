package com.edna.android.push.demo_x.pushdetail

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.databinding.PushDetailFragBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class PushDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<PushDetailViewModel> { viewModelFactory }

    private val args: PushDetailFragmentArgs by navArgs()

    private lateinit var viewDataBinding: PushDetailFragBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = PushDetailFragBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        viewDataBinding.arrowLeft.setOnClickListener {
            activity?.onBackPressed()
        }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setUpAdapter()
        viewModel.loadPushById(args.pushId, args.buttonAction, args.buttonActionTitle)
    }

    private fun setUpAdapter() {
        viewDataBinding.actionsList.adapter = ActionAdapter()

        val dividerItemDecoration = DividerItemDecorator(
            AppCompatResources.getDrawable(requireContext(), R.drawable.divider)
        )

        viewDataBinding.actionsList.addItemDecoration(dividerItemDecoration)
    }
}

class DividerItemDecorator(private val divider: Drawable?) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0..childCount - 2) {
            val child: View = parent.getChildAt(i)
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val dividerTop: Int = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + (divider?.intrinsicHeight ?: 0)
            divider?.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider?.draw(canvas)
        }
    }
}