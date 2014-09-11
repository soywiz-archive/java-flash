package com.soywiz.flash.display

import com.soywiz.flash.backend.Component

trait DisplayObjectBase extends Component {
  def stage: Stage
}
