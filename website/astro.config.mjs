import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';

export default defineConfig({
  site: 'https://aldefy.github.io',
  base: '/compose-shelf',
  integrations: [
    starlight({
      title: 'Compose Shelf',
      description: 'Material 3 Side Sheet for Compose Multiplatform',
      social: {
        github: 'https://github.com/aldefy/compose-shelf',
        'x.com': 'https://twitter.com/anthropicdev',
      },
      customCss: ['./src/styles/custom.css'],
      sidebar: [
        {
          label: 'Getting Started',
          items: [
            { label: 'Installation', link: '/getting-started/installation/' },
            { label: 'Quick Start', link: '/getting-started/quick-start/' },
          ],
        },
        {
          label: 'Guide',
          items: [
            { label: 'Modal Side Sheet', link: '/guide/modal-side-sheet/' },
            { label: 'Standard Side Sheet', link: '/guide/standard-side-sheet/' },
            { label: 'Adaptive Sheet', link: '/guide/adaptive-sheet/' },
            { label: 'Customization', link: '/guide/customization/' },
          ],
        },
        {
          label: 'API Reference',
          items: [
            { label: 'ModalSideSheet', link: '/api/modal-side-sheet/' },
            { label: 'StandardSideSheet', link: '/api/standard-side-sheet/' },
            { label: 'AdaptiveSheet', link: '/api/adaptive-sheet/' },
            { label: 'SideSheetState', link: '/api/side-sheet-state/' },
            { label: 'SideSheetDefaults', link: '/api/side-sheet-defaults/' },
          ],
        },
      ],
    }),
  ],
});
