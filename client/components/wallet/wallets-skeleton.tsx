export default function WalletsSkeleton() {
  return (
    <div className="grid grid-cols-3 gap-4">
      { [...Array(6)].map((_, i) => (
        <div key={ i } className="h-24 bg-gray-100 rounded-xl animate-pulse"></div>
      )) }
    </div>
  );
}
