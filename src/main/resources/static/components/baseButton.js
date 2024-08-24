/* eslint-disable no-unused-vars */
const BaseButton = {
    template: `
      <button 
          :type="type" 
          :class="mode" 
          @click="$emit('click')"> 
          <slot></slot>
      </button>
    `,
    props: {
      type: {
        type: String,
        default: 'button'
      },
      mode: {
        type: String,
        default: ''
      }
    }
  };
  